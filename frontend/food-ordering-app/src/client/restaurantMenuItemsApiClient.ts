import {MenuItem} from "../model/restaurant.ts";
import axios from "axios";

export const fetchRestaurantMenus = async (restaurantId: string): Promise<MenuItem[]> => {
  const response = await axios.get(`http://localhost:8095/api/catalog/restaurants/${restaurantId}/menu`);
  return response.data;
};