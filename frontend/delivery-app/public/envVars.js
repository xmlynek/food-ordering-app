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
      'delivery'),
  REACT_KEYCLOAK_CLIENT_ID: getDefault('${REACT_KEYCLOAK_CLIENT_ID}',
      'delivery-client-app'),
  REACT_DELIVERY_SERVICE_PATH: getDefault('${REACT_DELIVERY_SERVICE_PATH}',
      '/delivery-service/api/delivery'),
};