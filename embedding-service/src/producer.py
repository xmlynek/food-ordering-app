import json
import uuid
from kafka import KafkaProducer
from datetime import datetime
from config import KAFKA_BROKERS, EMBEDDING_OUTPUT_TOPIC, EMBEDDING_DEAD_LETTER_QUEUE_TOPIC

# Initialize a Kafka producer instance with JSON serialization.
producer = KafkaProducer(
    bootstrap_servers=KAFKA_BROKERS,
    value_serializer=lambda v: json.dumps(v).encode("utf-8")
)

def publish_embedding(restaurant_id, product_id, embeddings, original_headers):
  """
  Publish a message containing the calculated embedding, reusing header values from the consumer message.
  The new event message will follow this format:

  {
    "payload": "{\"restaurantId\":\"...\",\"productId\":\"...\",\"embedding\":[...]}",
    "headers": {
        ...reused headers...,
        "event-type": "com.food.ordering.app.common.event.RestaurantMenuItemImageEmbeddingsCalculatedEvent",
        "DATE": "<current GMT date>",
        "ID": "<new generated unique id>"
    }
  }
  """
  # Reuse original headers; make a copy to avoid side effects.
  headers = original_headers.copy() if original_headers else {}

  # Update only the fields that are specific to the embedding event.
  headers["event-type"] = "com.food.ordering.app.common.event.RestaurantMenuItemImageEmbeddingsCalculatedEvent"
  headers["event-aggregate-type"] = EMBEDDING_OUTPUT_TOPIC
  headers["DATE"] = datetime.utcnow().strftime("%a, %d %b %Y %H:%M:%S GMT")
  headers["ID"] = str(uuid.uuid4())

  # Build the payload as a JSON string.
  payload_obj = {
    "restaurantId": restaurant_id,
    "productId": product_id,
    "embeddings": embeddings
  }
  payload_str = json.dumps(payload_obj)

  message = {
    "payload": payload_str,
    "headers": headers
  }

  producer.send(EMBEDDING_OUTPUT_TOPIC, message)
  producer.flush()  # Ensure the message is sent immediately.

def publish_to_dlq(original_message, error_details):
  """
  Publish a message to the Dead-Letter Queue (DLQ) with additional error details.
  """
  dlq_message = {
    "original_message": original_message,
    "error": str(error_details),
    "timestamp": datetime.utcnow().strftime("%a, %d %b %Y %H:%M:%S GMT"),
    "dlq_id": str(uuid.uuid4())
  }

  producer.send(EMBEDDING_DEAD_LETTER_QUEUE_TOPIC, dlq_message)
  producer.flush()