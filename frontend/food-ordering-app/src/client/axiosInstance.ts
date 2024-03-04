import axios, {AxiosInstance, AxiosRequestConfig} from "axios";
import keycloak from "../keycloak/keycloak.ts";


// export const axiosInstance: AxiosInstance = axios.create({
//   baseURL:
// })
//
// axiosInstance.interceptors.request.use(
//     async (config: AxiosRequestConfig) => {
//
//       // Refresh the token if it has expired or has 10 or fewer seconds of validity remaining
//       await keycloak.updateToken(10);
//
//       const token = keycloak.token;
//
//       // @ts-ignore
//       config.headers.Authorization = `Bearer ${token}`;
//       // @ts-ignore
//       // config.headers["Access-Control-Allow-Origin"] = "*";
//
//       return config;
//     },
//     (error) => {
//       return Promise.reject(error);
//     }
// );
