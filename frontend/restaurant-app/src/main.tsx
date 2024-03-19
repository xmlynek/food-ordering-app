import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactKeycloakProvider} from "@react-keycloak/web";
import {Spin} from "antd";
import keycloak from "./keycloak/keycloak.ts";

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
          <App/>
        </QueryClientProvider>
      </React.StrictMode>
    </ReactKeycloakProvider>,
)
