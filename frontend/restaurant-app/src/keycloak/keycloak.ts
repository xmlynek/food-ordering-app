import Keycloak from 'keycloak-js'

// Setup Keycloak instance as needed
// Pass initialization options as required or leave blank to load from 'keycloak.json'
const keycloak = new Keycloak({
  "url": window.envVars.REACT_KEYCLOAK_URL,
  "realm": window.envVars.REACT_KEYCLOAK_REALM,
  "clientId": window.envVars.REACT_KEYCLOAK_CLIENT_ID,
})

export default keycloak;