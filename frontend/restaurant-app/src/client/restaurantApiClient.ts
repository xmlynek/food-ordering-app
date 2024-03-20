import {BasicRestaurantRestDto, Restaurant, RestaurantFormValues} from "../model/restaurant.ts";
import {axiosInstance} from "./axiosInstance.ts";

export const fetchRestaurantById = async (restaurantId: string): Promise<Restaurant> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}`);
  return response.data;
};

export const fetchRestaurants = async (): Promise<BasicRestaurantRestDto[]> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}`);
  return response.data;
};

export const createRestaurant = async (restaurantData: RestaurantFormValues): Promise<BasicRestaurantRestDto> => {
  const response = await axiosInstance.post(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}`, restaurantData);
  return response.data;
};

export const deleteRestaurant = async (restaurantId: string): Promise<void> => {
  const response = await axiosInstance.delete(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}`);
  return response.data;
};

export const updateRestaurant = async (restaurantId: string, restaurantData: RestaurantFormValues): Promise<BasicRestaurantRestDto> => {
  const response = await axiosInstance.put(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}`, restaurantData);
  return response.data;
};