import axios, {AxiosInstance, InternalAxiosRequestConfig} from "axios";
import keycloak from "../keycloak/keycloak.ts";


export const axiosInstance: AxiosInstance = axios.create({
  baseURL: window.envVars.REACT_API_GATEWAY_URL
})

axiosInstance.interceptors.request.use(async (config: InternalAxiosRequestConfig) => {

      // Refresh the token if it has expired or has 25 or fewer seconds of validity remaining
      await keycloak.updateToken(25);

      const token = keycloak.token;

      // @ts-ignore
      config.headers.Authorization = `Bearer ${token}`;
      // @ts-ignore
      // config.headers["Access-Control-Allow-Origin"] = "*";

      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);
