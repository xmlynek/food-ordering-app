import Keycloak from 'keycloak-js'

// Setup Keycloak instance as needed
// Pass initialization options as required or leave blank to load from 'keycloak.json'
// TODO: add values to config
const keycloak = new Keycloak({
  "url": "http://localhost:8083",
  "realm": "food-ordering-app",
  "clientId": "frontend"
})

export default keycloak;