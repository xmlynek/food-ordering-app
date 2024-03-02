export interface Order {
  id: string;
  createdAt: string;
  status: String; // TODO: make as enum
  totalPrice: number;
  orderItems: OrderItem[];
}

export interface OrderItem {
  id: string;
  orderTicketId: string;
  menuItemId: string;
  quantity: number;
  price: number;
}