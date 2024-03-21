import {Address} from "./address.ts";

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