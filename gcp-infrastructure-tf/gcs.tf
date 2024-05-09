resource "random_uuid" "rnd" {
}

# Create bucket
resource "google_storage_bucket" "food_ordering_system_bucket" {
  provider = google
  name     = "${var.gcs_bucket.name}-${random_uuid.rnd.result}"
  location = var.gcs_bucket.location
}

# Set the default object access control to public
resource "google_storage_default_object_access_control" "public_rule" {
  provider = google
  bucket   = google_storage_bucket.food_ordering_system_bucket.name
  role     = "READER"
  entity   = "allUsers"
}