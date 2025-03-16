FROM python:3.9-slim

# Set a working directory
WORKDIR /app

# Copy only necessary files first (to leverage Docker caching)
COPY requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application
COPY src ./src
COPY .env ./
COPY main.py ./

EXPOSE 5000

CMD ["python", "-m", "src.main"]