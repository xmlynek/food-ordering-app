import {FileType} from "../components/UI/UploadComponent.tsx";

export interface MenuItem {
  id: string;
  name: string;
  description: string;
  price: number;
  isAvailable: boolean;
  imageUrl: string;
}

export interface MenuItemFormValues {
  name: string;
  description: string;
  price: number;
  isAvailable?: boolean;
  image: FileType;
}