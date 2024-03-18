/// <reference types="vite/client" />
interface Window {
  envVars: {
    REACT_KEYCLOAK_URL: string,
    REACT_KEYCLOAK_REALM: string,
    REACT_KEYCLOAK_CLIENT_ID: string,
    REACT_API_GATEWAY_URL: string,
    REACT_ORDER_SERVICE_PATH: string,
    REACT_CATALOG_SERVICE_PATH: string,
    REACT_STRIPE_PUBLISHABLE_KEY: string,
  }
}