#!/bin/bash

# Define an associative array with repository names as keys and URLs as values
declare -A repositories=(
    ["keycloak"]="../../services/keycloak"
    ["zookeeper"]="../../services/zookeeper"
    ["kafka"]="../../services/kafka"
    ["cdc-service"]="../../services/cdc-service"
    ["order-service"]="../../services/order-service"
    ["kitchen-service"]="../../services/kitchen-service"
    ["catalog-service"]="../../services/catalog-service"
    ["delivery-service"]="../../services/delivery-service"
    ["payment-service"]="../../services/payment-service"
    ["restaurant-service"]="../../services/restaurant-service"
    ["api-gateway"]="../../services/api-gateway"
    ["food-ordering-app"]="../../applications/food-ordering-app"
    ["restaurant-app"]="../../applications/restaurant-app"
    ["delivery-app"]="../../applications/delivery-app"
)

CHARTS_VERSION="${CHARTS_VERSION:-"0.1.0"}"
CHART_REPOSITORY_URL="${CHART_REPOSITORY_URL}"

# Function to package and push charts
package_and_push() {
    local chart_dir=$1
    local repo_url=$2
    local chart_name
    chart_name=$(basename "$chart_dir")

    echo "Packaging Helm chart: $chart_name"
    helm package "$chart_dir" --destination build

    # Assuming a repository URL has been setup to receive chart packages
    # Here you might need a real URL and possibly credentials depending on your repository setup
    echo "Pushing $chart_name to repository"
    helm push "build/$chart_name-$CHARTS_VERSION.tgz" "$repo_url"
}

# Directory to store packaged charts
mkdir -p build

# Package and push each chart
for chart in "${repositories[@]}"; do
    if [ -d "$chart" ]; then
        package_and_push "$chart" "$CHART_REPOSITORY_URL"
    fi
done

# Clean up packaged charts
rm -rf build
echo "All charts have been processed."
