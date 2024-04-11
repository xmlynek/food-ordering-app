import {axiosInstance} from "./axiosInstance.ts";
import {MenuItemRestDTO} from "../model/restApiDto.ts";
import {PageableRestApiResponse} from "../model/pageable.ts";

export const fetchRestaurantMenus = async (restaurantId: string, page: number = 0, size: number = 10): Promise<PageableRestApiResponse<MenuItemRestDTO>> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_CATALOG_SERVICE_PATH}/${restaurantId}/menu?page=${page}&size=${size}`);
  return response.data;
};