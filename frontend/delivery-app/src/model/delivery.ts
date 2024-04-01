import {Address} from "./address.ts";

export interface Delivery {
  id: string;
  customerId: string;
  courierId: string;
  restaurantId: string;
  restaurantName: string;
  deliveryStatus: DeliveryStatus;
  kitchenTicketStatus: KitchenTicketStatus;
  lastModifiedAt: string;
  deliveryAddress: Address;
  restaurantAddress: Address;
}

export enum DeliveryStatus {
  WAITING_FOR_KITCHEN = "WAITING_FOR_KITCHEN",
  AT_DELIVERY = "AT_DELIVERY",
  DELIVERED = "DELIVERED",
}

export enum KitchenTicketStatus {
  PREPARING = "PREPARING",
  READY_FOR_DELIVERY = "READY_FOR_DELIVERY",
  REJECTED = "REJECTED",
  FINISHED = "FINISHED"
}
