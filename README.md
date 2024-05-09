# Food ordering system

## Quickstart

### Prerequisites

- JDK 21
- Node.js 21
- Stripe API keys
- GCP project
- GCS bucket with public access
- GCP service account with access to the bucket

### Quick start - docker compose

In order to run the whole application with docker compose, you need to follow the steps below:

1. Set `REACT_STRIPE_PUBLISHABLE_KEY` and `STRIPE_SECRET_KEY` in [.env](./.env) file with your
   Stripe API keys
2. Add or replace GCP service account secret
   key [JSON file](./volumes/restaurant-service/YOUR_GCP_KEY.example.json)
   in [./volumes/restaurant-service](./volumes/restaurant-service) directory
3. Set `GCP_STORAGE_BUCKET_NAME`, `GCP_STORAGE_PROJECT_ID` and `GCP_KEY_FILE_NAME` in [.env](./.env)
   with
   your GCP bucket name, project id and Service account secret key file name
4. Run docker compose `run docker-compose up`

### Quick start - development

In order to run the application in development mode to add new features, you need to follow the
steps below:

1. Install all modules
   `install food-ordering-system modules` or
   run `mvn clean install -DskipTests -P install-frontends`
2. Set `REACT_STRIPE_PUBLISHABLE_KEY` default value in
   food-ordering-app [envVars.js](./frontend/food-ordering-app/public/envVars.js)
3. Set `STRIPE_API_KEY` with your Stripe secret key in payment
   service [application-dev.properties](./payment-service/src/main/resources/application-dev.yaml)
4. Move GCP service account secret key to
   restaurant-service [resources](./restaurant-service/src/main/resources)
5. Set `GCP_STORAGE_BUCKET_NAME`, `GCP_STORAGE_PROJECT_ID` and `GCP_CREDENTIALS_LOCATION` in
   restaurant
   service [application-dev.properties](./restaurant-service/src/main/resources/application-dev.yaml)
6. Run docker compose dev `run docker-compose.dev up`
7. Run the application `start food-ordering-system`



## Create GCP infrastructure using Terraform

You can use Terraform to create whole GCP infrastructure. You need to have Terraform installed on
your
machine and follow the steps below:

1. Move to the [gcp-infrastructure-tf](./gcp-infrastructure-tf) directory
2. Copy your GCP service account secret key to
   the [credentials.json](./gcp-infrastructure-tf/credentials.json) file
3. Rename [terraform.tfvars.example](./gcp-infrastructure-tf/terraform.tfvars.example)
   to `terraform.tfvars` and set the required variables
4. Run `terraform init`
5. Run `terraform apply`

If you encounter any issues related to service API activation, you will need to enable the required
APIs manually.
