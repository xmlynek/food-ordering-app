variable "gcp_credentials" {
  default = "./credentials.json"

  validation {
    condition     = length(var.gcp_credentials) > 0
    error_message = "Credentials cannot be empty"
  }
}

variable "gcp_project" {

  validation {
    condition     = length(var.gcp_project) > 0
    error_message = "Project id cannot be empty"
  }
}

variable "dns_name" {

  validation {
    condition     = length(var.dns_name) > 0
    error_message = "DNS name cannot be empty"
  }
}

variable "gcp_region" {
  default = "europe-central2"

  validation {
    condition     = length(var.gcp_region) > 0
    error_message = "Region cannot be empty"
  }
}

variable "gcp_zone" {
  default = "europe-central2-a"

  validation {
    condition     = length(var.gcp_zone) > 0
    error_message = "Zone cannot be empty"
  }
}

variable "gke_cluster" {
  type = object({
    name                               = string
    location                           = string
    disable_horizontal_pod_autoscaling = bool
  })
  default = {
    name                               = "primary",
    location                           = "europe-central2-a",
    disable_horizontal_pod_autoscaling = false
  }

  description = "GKE cluster configuration"

  validation {
    condition     = length(var.gke_cluster.name) > 0
    error_message = "The cluster name must not be empty."
  }

  validation {
    condition     = length(var.gke_cluster.location) > 0
    error_message = "The cluster location must not be empty."
  }
}

variable "gke_node_pool" {
  type = object({
    name = string
    autoscaling = object({
      min_node_count = number
      max_node_count = number
    })
    machine_type = string
  })
  default = {
    name = "general",
    autoscaling = {
      min_node_count = 0,
      max_node_count = 3
    }
    machine_type = "e2-standard-4"
  }

  description = "GKE node pool configuration"

  validation {
    condition     = length(var.gke_node_pool.name) > 0
    error_message = "The node pool name must not be empty."
  }

  validation {
    condition     = var.gke_node_pool.autoscaling.min_node_count >= 0 && var.gke_node_pool.autoscaling.min_node_count <= var.gke_node_pool.autoscaling.max_node_count
    error_message = "The autoscaling minimum node count must be non-negative and less than or equal to the maximum node count."
  }

  validation {
    condition     = length(var.gke_node_pool.machine_type) > 0
    error_message = "The machine type must not be empty."
  }
}

variable "gcs_bucket" {
  type = object({
    name     = string
    location = string
  })
  default = {
    name     = "food-ordering-app-media",
    location = "EU"
  }
  description = "GCS bucket to store media files"

  validation {
    condition     = length(var.gcs_bucket.name) > 0
    error_message = "Bucket name cannot be empty"
  }

  validation {
    condition     = length(var.gcs_bucket.location) > 0
    error_message = "Bucket location cannot be empty"
  }
}