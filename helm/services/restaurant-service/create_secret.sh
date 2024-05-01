#!/bin/bash

# Define the directory where the JSON key file is located
JSON_KEY_DIR="$(dirname "${BASH_SOURCE[0]}")"

# Define the filename of the JSON key file
JSON_KEY_FILE="${JSON_KEY_FILE:-YOUR_GCP_KEY.json}"

# Combine directory and filename into a full path
JSON_KEY_PATH="${JSON_KEY_DIR}/${JSON_KEY_FILE}"

# Define the name of the secret
SECRET_NAME="gcp-key"

# Check if the JSON file exists
if [ ! -f "$JSON_KEY_PATH" ]; then
    echo "Error: JSON key file not found at $JSON_KEY_PATH"
    exit 1
fi

# Create or replace the Kubernetes secret
echo "Creating or updating the secret $SECRET_NAME from the JSON key file..."
kubectl create secret generic $SECRET_NAME --from-file="$JSON_KEY_PATH" --dry-run=client -o yaml | kubectl apply -f -

# Check the command result
if [ $? -eq 0 ]; then
    echo "Secret $SECRET_NAME created or updated successfully."
else
    echo "Failed to create or update the secret $SECRET_NAME."
    exit 1
fi
