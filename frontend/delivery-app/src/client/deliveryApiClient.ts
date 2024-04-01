import {axiosInstance} from "./axiosInstance.ts";
import {PageableRestApiResponse} from "../model/pageable.ts";
import {Delivery} from "../model/delivery.ts";

export const fetchDeliveryById = async (deliveryId: string): Promise<Delivery> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}/${deliveryId}`);
  return response.data;
};

export const fetchDeliveries = async (page: number = 0, size: number = 10): Promise<PageableRestApiResponse<Delivery>> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}?page=${page}&size=${size}`);
  return response.data;
};

export const assignDelivery = async (deliveryId: string): Promise<void> => {
  const response = await axiosInstance.post(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}/${deliveryId}/assign`);
  return response.data;
};

export const pickUpDelivery = async (deliveryId: string): Promise<void> => {
  const response = await axiosInstance.post(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}/${deliveryId}/pick-up`);
  return response.data;
};

export const completeDelivery = async (deliveryId: string): Promise<void> => {
  const response = await axiosInstance.post(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}/${deliveryId}/complete`);
  return response.data;
};
