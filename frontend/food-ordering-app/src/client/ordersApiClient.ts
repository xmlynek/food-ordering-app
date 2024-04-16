import {axiosInstance} from "./axiosInstance.ts";
import {CreateOrderDto, OrderDetailsDto, OrderHistoryDto} from "../model/order.ts";
import {PageableRestApiResponse} from "../model/pageable.ts";


export const postOrder = async (orderData: CreateOrderDto) => {
  const response = await axiosInstance.post(window.envVars.REACT_ORDER_SERVICE_PATH, orderData);
  return response.data;
}

export const fetchOrderHistory = async (customerId: string, currentPage: number = 0, pageSize: number = 10): Promise<PageableRestApiResponse<OrderHistoryDto>> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_ORDER_SERVICE_PATH}/customer/${customerId}?page=${currentPage}&size=${pageSize}`);
  return response.data;
}

export const fetchOrderDetails = async (orderId: string): Promise<OrderDetailsDto> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_ORDER_SERVICE_PATH}/${orderId}`);
  return response.data;
}