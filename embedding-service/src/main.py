import threading
from web import app
from consumer import consume_kafka_messages

def main():
  # Start Kafka consumer in a separate thread
  threading.Thread(target=consume_kafka_messages, daemon=True).start()
  # Run Flask app for synchronous requests
  app.run(host="0.0.0.0", port=5000)

if __name__ == "__main__":
  main()
