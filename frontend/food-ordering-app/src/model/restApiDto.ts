import {Address} from "./address.ts";

export interface BasicRestaurantRestDTO {
  id: string;
  name: string;
  description: string;
  address: Address;
  isAvailable: boolean;
}

export interface FullRestaurantRestDTO {
  id: string;
  name: string;
  description: string;
  address: Address;
  isAvailable: boolean;
  createdAt: string;
  lastModifiedAt: string;
  menuItems: MenuItemRestDTO[];
}

export interface MenuItemRestDTO {
  id: string;
  name: string;
  description: string;
  price: number;
  isAvailable: boolean;
  createdAt: string;
  lastModifiedAt: string;
  imageUrl: string;
}