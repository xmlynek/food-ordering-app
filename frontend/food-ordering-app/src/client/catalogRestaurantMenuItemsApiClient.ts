import {axiosInstance} from "./axiosInstance.ts";
import {MenuItemRestDTO} from "../model/restApiDto.ts";
import {PageableRestApiResponse} from "../model/pageable.ts";

export const fetchRestaurantMenus = async (restaurantId: string): Promise<PageableRestApiResponse<MenuItemRestDTO>> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_CATALOG_SERVICE_PATH}/${restaurantId}/menu`);
  return response.data;
};