import json
import time
import requests
from kafka import KafkaConsumer
from embeddings import get_image_embedding
from producer import publish_embedding, publish_to_dlq
from config import (
  KAFKA_BROKERS,
  KAFKA_TOPIC,
  KAFKA_GROUP_ID,
  KAFKA_AUTO_OFFSET_RESET,
  KAFKA_CONSUMER_MAX_RETRIES,
  KAFKA_CONSUMER_RETRY_DELAY_SECONDS,
  ALLOWED_EVENT_TYPES
)


def process_message(message):
  """
  Process a Kafka message:
    - Parse the payload and headers.
    - Download the image from imageUrl.
    - Compute the image embedding.
    - Optionally append the embedding to a CSV file.
    - Publish the calculated embedding.
  Returns True if processing succeeds, False otherwise.
  """
  try:
    # Decode the outer message.
    outer_data = json.loads(message.value.decode("utf-8"))
    payload = json.loads(outer_data.get("payload", "{}"))
    original_headers = outer_data.get("headers", {})

    event_type = original_headers.get("event-type")
    if event_type not in ALLOWED_EVENT_TYPES:
      print("Skipping message with unsupported event-type:", event_type)
      return True  # Skip processing but commit the offset.

    # Extract values from payload and headers.
    restaurant_id = payload.get("restaurantId")
    product_id = original_headers.get("event-aggregate-id")
    image_url = payload.get("imageUrl")

    if not restaurant_id or not product_id or not image_url:
      print("Invalid message format: missing restaurantId, productId, or imageUrl", payload)
      return False

    # Download the image.
    response = requests.get(image_url)
    if response.status_code != 200:
      print(f"Failed to download image from {image_url}, status code: {response.status_code}")
      return False
    image_bytes = response.content

    # Compute the embedding.
    embedding = get_image_embedding(image_bytes)

    # Publish the calculated embedding.
    publish_embedding(restaurant_id, product_id, embedding, original_headers)
    print(f"Processed restaurant {restaurant_id} and published embedding for product {product_id} with event type {event_type}.")
    return True
  except Exception as e:
    print("Error processing message:", e)
    return False


def consume_kafka_messages():
  """
  Create a Kafka consumer that processes messages with manual offset commits.
  If a message fails processing after MAX_RETRIES, it is sent to a Dead-Letter Queue.
  """
  consumer = KafkaConsumer(
      KAFKA_TOPIC,
      bootstrap_servers=KAFKA_BROKERS,
      auto_offset_reset=KAFKA_AUTO_OFFSET_RESET,
      group_id=KAFKA_GROUP_ID,
      enable_auto_commit=False
  )
  print("Kafka consumer started, listening for messages on topic:", KAFKA_TOPIC)

  for message in consumer:
    attempt = 0
    processed = False
    while attempt < KAFKA_CONSUMER_MAX_RETRIES and not processed:
      processed = process_message(message)
      if not processed:
        attempt += 1
        print(f"Retrying message, attempt {attempt}/{KAFKA_CONSUMER_MAX_RETRIES}...")
        time.sleep(KAFKA_CONSUMER_RETRY_DELAY_SECONDS)

    if not processed:
      # Send the original message to the DLQ.
      print("Sending message to DLQ after maximum retries.")
      publish_to_dlq(message.value.decode("utf-8"), "Max retries reached")

    # Commit the offset regardless of processing outcome.
    consumer.commit()
