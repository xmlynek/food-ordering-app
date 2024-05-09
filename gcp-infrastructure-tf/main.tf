# Create static IP addresses for the ingresses
resource "google_compute_global_address" "food_ordering_app_ingress_ip" {
  provider    = google
  name        = "food-ordering-ingress-static-ip"
  description = "Static IP for the food ordering app ingress"
}

resource "google_compute_global_address" "restaurant_app_ingress_ip" {
  provider    = google
  name        = "restaurant-ingress-static-ip"
  description = "Static IP for the restaurant app ingress"
}

resource "google_compute_global_address" "delivery_app_ingress_ip" {
  provider    = google
  name        = "delivery-ingress-static-ip"
  description = "Static IP for the delivery app ingress"
}

resource "google_compute_global_address" "api_gateway_ingress_ip" {
  provider    = google
  name        = "api-gateway-ingress-static-ip"
  description = "Static IP for the API gateway ingress"
}

# Create a new DNS zone
resource "google_dns_managed_zone" "food_ordering_system_zone" {
  provider    = google
  name        = "food-ordering-system-zone"
  dns_name    = var.dns_name
  description = "Food ordering system DNS zone"
}

# Create DNS record for the food ordering app
resource "google_dns_record_set" "food_ordering_app_rs" {
  provider     = google
  name         = google_dns_managed_zone.food_ordering_system_zone.dns_name
  type         = "A"
  ttl          = 300
  managed_zone = google_dns_managed_zone.food_ordering_system_zone.name
  rrdatas      = [google_compute_global_address.food_ordering_app_ingress_ip.address]
}

# Create DNS record for the restaurant app
resource "google_dns_record_set" "restaurant_app_rs" {
  provider     = google
  name         = "restaurant.${google_dns_managed_zone.food_ordering_system_zone.dns_name}"
  type         = "A"
  ttl          = 300
  managed_zone = google_dns_managed_zone.food_ordering_system_zone.name
  rrdatas      = [google_compute_global_address.restaurant_app_ingress_ip.address]
}

# Create DNS record for the delivery app
resource "google_dns_record_set" "delivery_app_rs" {
  provider     = google
  name         = "delivery.${google_dns_managed_zone.food_ordering_system_zone.dns_name}"
  type         = "A"
  ttl          = 300
  managed_zone = google_dns_managed_zone.food_ordering_system_zone.name
  rrdatas      = [google_compute_global_address.delivery_app_ingress_ip.address]
}

# Create DNS record for the api gateway
resource "google_dns_record_set" "api_gateway_rs" {
  provider     = google
  name         = "api-gw.${google_dns_managed_zone.food_ordering_system_zone.dns_name}"
  type         = "A"
  ttl          = 300
  managed_zone = google_dns_managed_zone.food_ordering_system_zone.name
  rrdatas      = [google_compute_global_address.api_gateway_ingress_ip.address]
}
