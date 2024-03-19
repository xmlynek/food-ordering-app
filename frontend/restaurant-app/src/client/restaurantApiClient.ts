import {Restaurant} from "../model/restaurant.ts";
import {axiosInstance} from "./axiosInstance.ts";

export const fetchRestaurantById = async (restaurantId: string): Promise<Restaurant> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}`);
  return response.data;
};

export const fetchRestaurants = async (): Promise<Restaurant[]> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}`);
  return response.data;
};

export const createRestaurant = async (restaurantData) => {
  const response = await axiosInstance.post(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}`, restaurantData);
  return response.data;
};