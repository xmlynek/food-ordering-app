import {Address} from "./address.ts";
import {MenuItem} from "./menuItem.ts";

export interface Restaurant {
  id: string;
  name: string;
  description: string;
  address: Address;
  isAvailable: boolean;
  menuItems: MenuItem[];
}

export interface BasicRestaurantRestDto {
  id: string;
  name: string;
  description: string;
  address: Address;
  isAvailable: boolean;
}

export interface RestaurantFormValues {
  name: string;
  description: string;
  isAvailable?: boolean;
  address: Address;
}
