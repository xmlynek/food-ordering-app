import io
import torch
from PIL import Image
from transformers import AutoImageProcessor, ViTModel
from config import MODEL_NAME

# Determine device (use "cuda" if GPU is available, otherwise "cpu")
device = "cuda" if torch.cuda.is_available() else "cpu"

# Load the feature extractor and ViT model from Hugging Face
image_processor = AutoImageProcessor.from_pretrained(MODEL_NAME)
model = ViTModel.from_pretrained(MODEL_NAME).to(device)

def get_image_embedding(image_bytes: bytes):
  """
  Given image bytes, preprocess and extract an embedding (using the CLS token).
  """
  image = Image.open(io.BytesIO(image_bytes)).convert("RGB")
  inputs = image_processor(images=image, return_tensors="pt")
  inputs = {k: v.to(device) for k, v in inputs.items()}
  with torch.no_grad():
    outputs = model(**inputs)
  # Use the first token (CLS token) as the embedding
  embedding = outputs.last_hidden_state[:, 0, :]
  return embedding.cpu().numpy().tolist()[0]
