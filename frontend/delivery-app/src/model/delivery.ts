import {Address} from "./address.ts";

export interface Delivery {
  id: string;
  customerId: string;
  courierId: string;
  restaurantId: string;
  restaurantName: string;
  deliveryStatus: DeliveryStatus;
  lastModifiedAt: string;
  deliveryAddress: Address;
  restaurantAddress: Address;
}

export enum DeliveryStatus {
  WAITING_FOR_KITCHEN = "WAITING_FOR_KITCHEN",
  READY_FOR_DELIVERY = "READY_FOR_DELIVERY",
  AT_DELIVERY = "AT_DELIVERY",
  DELIVERED = "DELIVERED",
}