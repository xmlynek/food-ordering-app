import {axiosInstance} from "./axiosInstance.ts";
import {PageableRestApiResponse} from "../model/pageable.ts";
import {MenuItem, MenuItemFormValues} from "../model/menuItem.ts";

export const fetchRestaurantMenuItems = async (restaurantId: string, page: number = 0, size: number = 10): Promise<PageableRestApiResponse<MenuItem>> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}/menu?page=${page}&size=${size}`);
  return response.data;
};

export const fetchRestaurantMenuItem = async (restaurantId: string, menuId: string) => {
  const response = await axiosInstance.get(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}/menu/${menuId}`);
  return response.data;
};

export const deleteRestaurantMenuItem = async (restaurantId: string, menuId: string): Promise<void> => {
  const response = await axiosInstance.delete(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}/menu/${menuId}`);
  return response.data;
}

export const updateRestaurantMenuItem = async (restaurantId: string, menuId: string, menuData: MenuItemFormValues) => {
  const formData = new FormData();

  formData.append('menuItemUpdateRequest', new Blob([JSON.stringify({
    name: menuData.name,
    description: menuData.description,
    price: menuData.price,
    isAvailable: menuData.isAvailable,
  })], {type: 'application/json'}));

  formData.append('image', menuData.image);

  const response = await axiosInstance.put(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}/menu/${menuId}`, formData,
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          }
      )
  ;
  return response.data;
};

export const createRestaurantMenuItem = async (restaurantId: string, menuData: MenuItemFormValues) => {
  const formData = new FormData();
  formData.append('menuItemRequest', new Blob([JSON.stringify({
    name: menuData.name,
    description: menuData.description,
    price: menuData.price
  })], {type: 'application/json'}));
  formData.append('image', menuData.image);

  const response = await axiosInstance.post(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}/menu`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });

  return response.data;
};
