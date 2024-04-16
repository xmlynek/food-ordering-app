function getDefault(envVar, defaultValue) {
  if (envVar.match(/\$\{[A-Z_]+}/gm)) {
    return defaultValue;
  }
  return envVar;
}

/* eslint-disable no-template-curly-in-string */
window.envVars = {
  REACT_API_GATEWAY_URL: getDefault('${REACT_API_GATEWAY_URL}',
      'http://localhost:8060'),
  REACT_KEYCLOAK_URL: getDefault('${REACT_KEYCLOAK_URL}',
      'http://localhost:8083'),
  REACT_KEYCLOAK_REALM: getDefault('${REACT_KEYCLOAK_REALM}',
      'food-ordering-app'),
  REACT_KEYCLOAK_CLIENT_ID: getDefault('${REACT_KEYCLOAK_CLIENT_ID}',
      'frontend'),
  REACT_ORDER_SERVICE_PATH: getDefault('${REACT_ORDER_SERVICE_PATH}',
      '/order-service/api/orders'),
  REACT_CATALOG_SERVICE_PATH: getDefault('${REACT_CATALOG_SERVICE_PATH}',
      '/catalog-service/api/catalog/restaurants'),
  REACT_STRIPE_PUBLISHABLE_KEY: getDefault('${REACT_STRIPE_PUBLISHABLE_KEY}',
      'pk_test_51Oc4G2Hg2RuOlHnDqgW42ddAokEcXPW0MSOEqtKbMUCKGPTNAdXC9ui5BapPvOr59BXFdSeaObNTOVYqEQUsOgO6001DCDN9Iw'),
};