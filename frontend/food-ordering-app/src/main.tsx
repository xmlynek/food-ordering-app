import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {BasketProvider} from "./context/BasketContext.tsx";
import {ReactKeycloakProvider} from "@react-keycloak/web";
import keycloak from "./keycloak/keycloak.ts";
import {Spin} from "antd";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      // suspense: true,
    },
  },
})

ReactDOM.createRoot(document.getElementById('root')!).render(
    <ReactKeycloakProvider authClient={keycloak}
                           initOptions={{onLoad: 'login-required', pkceMethod: "S256"}}
                           autoRefreshToken={false}
                           LoadingComponent={<Spin fullscreen tip={"Loading..."} spinning={true}/>}>
      <React.StrictMode>
        <QueryClientProvider client={queryClient}>
          <BasketProvider>
            <App/>
          </BasketProvider>
        </QueryClientProvider>
      </React.StrictMode>
    </ReactKeycloakProvider>,
)
