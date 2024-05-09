resource "google_service_account" "kubernetes" {
  account_id   = "kubernetes"
  display_name = "Service Account"
}


resource "google_container_cluster" "primary" {
  name     = var.gke_cluster.name
  location = var.gke_cluster.location

  remove_default_node_pool = true
  initial_node_count       = 1

  logging_service    = "logging.googleapis.com/kubernetes"
  monitoring_service = "monitoring.googleapis.com/kubernetes"

  addons_config {
    horizontal_pod_autoscaling {
      disabled = var.gke_cluster.disable_horizontal_pod_autoscaling
    }
  }
}

resource "google_container_node_pool" "general" {
  name               = var.gke_node_pool.name
  cluster            = google_container_cluster.primary.id
  initial_node_count = 2

  management {
    auto_repair  = true
    auto_upgrade = false
  }

  autoscaling {
    min_node_count = var.gke_node_pool.autoscaling.min_node_count
    max_node_count = var.gke_node_pool.autoscaling.max_node_count
  }

  node_config {
    preemptible  = true
    machine_type = var.gke_node_pool.machine_type

    service_account = google_service_account.kubernetes.email
    oauth_scopes    = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }
}