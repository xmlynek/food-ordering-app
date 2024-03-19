import {FileType} from "../components/UI/UploadComponent.tsx";

export interface Restaurant {
  id: string;
  name: string;
  isAvailable: boolean;
  menuItems: MenuItem[];
}

export interface MenuItem {
  id: string;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
}

export interface MenuItemFormValues {
  name: string;
  description: string;
  price: number;
  image: FileType;
}

export interface RestaurantFormValues {
  name: string;
  description: string;
  address: {
    street: string;
    postalCode: string;
    city: string;
  };
}