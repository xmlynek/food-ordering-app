#!/bin/bash

# Define the directory containing the files you want to include in the ConfigMap
DIRECTORY="$(dirname "${BASH_SOURCE[0]}")/export"
# Define the name of the ConfigMap
CONFIGMAP_NAME="keycloak-configmap"
# Specify the Kubernetes namespace where the ConfigMap will be created
NAMESPACE="default"

# Check if the directory exists
if [ ! -d "$DIRECTORY" ]; then
  echo "Directory $DIRECTORY does not exist."
  exit 1
fi

# Create or update the ConfigMap
# This command assumes you want to overwrite an existing ConfigMap of the same name
echo "Creating or updating ConfigMap $CONFIGMAP_NAME from files in $DIRECTORY"
kubectl create configmap $CONFIGMAP_NAME --from-file="$DIRECTORY" --namespace=$NAMESPACE --dry-run=client -o yaml | kubectl apply -f -

# Verify and output the result
if [ $? -eq 0 ]; then
  echo "ConfigMap $CONFIGMAP_NAME created/updated successfully."
else
  echo "Failed to create/update ConfigMap $CONFIGMAP_NAME."
  exit 1
fi
