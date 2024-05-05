#!/bin/bash
# Script to install a Helm chart with dependencies (Secret and ConfigMap)

SCRIPT_DIR=$(dirname "$(readlink -f "$0")")

echo "Starting the installation process..."

# Step 1: Create or update the secret
CREATE_SECRET_SCRIPT_DIRECTORY="$SCRIPT_DIR/../../services/restaurant-service"

# Using "source" to execute the script to preserve any environment variables it might set (if needed)
source "$CREATE_SECRET_SCRIPT_DIRECTORY/create_secret.sh"
if [ $? -ne 0 ]; then
    echo "Failed to create or update the secret. Aborting installation."
    exit 1
fi

# Step 2: Create or update the ConfigMap
CREATE_CONFIGMAP_SCRIPT_DIRECTORY="$SCRIPT_DIR/../../services/keycloak"

# Using "source" to execute the script, ensuring it runs in the current shell context
source "$CREATE_CONFIGMAP_SCRIPT_DIRECTORY/create_configmap.sh"
if [ $? -ne 0 ]; then
    echo "Failed to create or update the ConfigMap. Aborting installation."
    exit 1
fi

# Step 3: Apply the Helmfile

echo "Applying the Helmfile..."
helmfile apply
if [ $? -eq 0 ]; then
    echo "Helmfile applied successfully."
else
    echo "Failed to apply the Helmfile."
    exit 1
fi
