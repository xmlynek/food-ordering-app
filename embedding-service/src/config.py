import os
from dotenv import load_dotenv

load_dotenv()

KAFKA_BROKERS = os.environ.get('KAFKA_BROKERS', 'localhost:9092').split(',')
KAFKA_TOPIC = os.environ.get('KAFKA_TOPIC',
                             'com.food.ordering.app.restaurant.service.entity.MenuItem')
KAFKA_GROUP_ID = os.environ.get('KAFKA_GROUP_ID', 'embedding_service')
KAFKA_AUTO_OFFSET_RESET = os.environ.get('KAFKA_AUTO_OFFSET_RESET', 'latest')
MODEL_NAME = os.environ.get('MODEL_NAME', "google/vit-base-patch16-224-in21k")
EMBEDDING_OUTPUT_TOPIC = os.environ.get('EMBEDDING_OUTPUT_TOPIC',
                                        "com.food.ordering.app.restaurant.service.entity.MenuItemEmbeddings")
EMBEDDING_DEAD_LETTER_QUEUE_TOPIC = os.environ.get(
    'EMBEDDING_DEAD_LETTER_QUEUE_TOPIC', 'embeddings_dead_letter_queue')
KAFKA_CONSUMER_MAX_RETRIES = int(
    os.environ.get('KAFKA_CONSUMER_MAX_RETRIES', 3))
KAFKA_CONSUMER_RETRY_DELAY_SECONDS = int(
    os.environ.get('KAFKA_CONSUMER_RETRY_DELAY_SECONDS', 5))
ALLOWED_EVENT_TYPES = os.environ.get(
    'ALLOWED_EVENT_TYPES',
    "com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent,com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent"
).split(',')
