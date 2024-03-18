import {axiosInstance} from "./axiosInstance.ts";
import {PageableRestApiResponse} from "../model/pageable.ts";
import {BasicRestaurantRestDTO, FullRestaurantRestDTO} from "../model/restApiDto.ts";

export const fetchRestaurantById = async (restaurantId: string): Promise<FullRestaurantRestDTO> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_CATALOG_SERVICE_PATH}/${restaurantId}`);
  return response.data;
};

export const fetchRestaurants = async (page: number = 0, size: number = 10, searchValue: string = ''): Promise<PageableRestApiResponse<BasicRestaurantRestDTO>> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_CATALOG_SERVICE_PATH}?page=${page}&size=${size}&searchValue=${searchValue}`);
  return response.data;
};