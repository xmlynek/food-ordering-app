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

// export const createRestaurant = async (restaurantData: RestaurantFormValues): Promise<BasicRestaurantRestDto> => {
//   const response = await axiosInstance.post(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}`, restaurantData);
//   return response.data;
// };
//
// export const deleteRestaurant = async (restaurantId: string): Promise<void> => {
//   const response = await axiosInstance.delete(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}/${restaurantId}`);
//   return response.data;
// };
//
// export const updateRestaurant = async (restaurantId: string, restaurantData: RestaurantFormValues): Promise<BasicRestaurantRestDto> => {
//   const response = await axiosInstance.put(`${window.envVars.REACT_DELIVERY_SERVICE_PATH}/${restaurantId}`, restaurantData);
//   return response.data;
// };