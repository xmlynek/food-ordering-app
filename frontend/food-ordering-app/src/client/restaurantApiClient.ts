import {Restaurant} from "../model/restaurant.ts";

export const fetchRestaurantById = async (restaurantId: string): Promise<Restaurant> => {
  const response = await fetch(`http://localhost:8095/api/catalog/restaurants/${restaurantId}`);
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json();
};

export const fetchRestaurants = async (): Promise<Restaurant[]> => {
  const response = await fetch('http://localhost:8095/api/catalog/restaurants');
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json();
};