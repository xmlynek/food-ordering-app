import {Address} from "./address.ts";
import {DeliveryStatus, KitchenTicketStatus, OrderStatus} from "./enum.ts";

export interface CreateOrderDto {
  customerId: string;
  restaurantId: string;
  paymentToken: string;
  address: Address;
  totalPrice: string;
  items: CreateOrderItemDto[];
}

export interface CreateOrderItemDto {
  productId: string;
  quantity: number;
  price: string;
}

export interface OrderHistoryDto {
  id: string;
  restaurantId: string;
  totalPrice: number;
  createdAt: string;
  orderStatus: OrderStatus;
  kitchenTicketStatus: KitchenTicketStatus;
  deliveryStatus: DeliveryStatus;
  failureMessage: string;
}

export interface OrderDetailsDto extends OrderHistoryDto {
  items: OrderItemDetailsDto[];
  lastModifiedAt: string;
}

export interface OrderItemDetailsDto {
  productId: string;
  name: string;
  description: string;
  quantity: number;
  price: number;
  imageUrl: string;
}