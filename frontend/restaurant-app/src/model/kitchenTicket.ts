import {DeliveryStatus, KitchenTicketStatus} from "./enum.ts";

export interface BasicKitchenTicketRestDTO {
  id: string;
  createdAt: string;
  lastModifiedAt: string;
  status: KitchenTicketStatus;
  deliveryStatus: DeliveryStatus;
  totalPrice: number;
}

export interface KitchenTicketDetailsRestDTO extends BasicKitchenTicketRestDTO {
  ticketItems: KitchenTicketItemDetailsRestDTO[];
}


export interface KitchenTicketItemDetailsRestDTO {
  id: string;
  menuItemId: string;
  quantity: number;
  price: number;
  name: string;
  imageUrl: string;
}