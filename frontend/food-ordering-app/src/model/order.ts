export interface CreateOrderDto {
  customerId: string;
  restaurantId: string;
  paymentToken: string;
  address: Address;
  totalPrice: string;
  items: CreateOrderItemDto[];
}

export interface Address {
  street: string;
  postalCode: string;
  city: string;
}

export interface CreateOrderItemDto {
  productId: string;
  quantity: number;
  price: string;
}