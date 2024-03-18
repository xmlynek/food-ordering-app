import {axiosInstance} from "./axiosInstance.ts";
import {CreateOrderDto} from "../model/order.ts";


export const postOrder = async (orderData: CreateOrderDto) => {
  const response = await axiosInstance.post(window.envVars.REACT_ORDER_SERVICE_PATH, orderData);
  return response.data;
}