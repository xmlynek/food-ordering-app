from flask import Flask, request, jsonify
from PIL import Image
import io
from embeddings import get_image_embedding

app = Flask(__name__)

@app.route('/api/embeddings', methods=['POST'])
def embeddings():
  if 'image' not in request.files:
    return jsonify({'error': 'No image provided'}), 400
  file = request.files['image']
  # Read the raw bytes from the file
  image_bytes = file.read()
  try:
    # Use BytesIO to convert raw bytes to a stream for PIL
    image = Image.open(io.BytesIO(image_bytes))
    # Process the image and compute embeddings
    embeddings = get_image_embedding(image_bytes)  # your embedding logic here
    return jsonify({'embeddings': embeddings})
  except Exception as e:
    return jsonify({'error': str(e)}), 500
