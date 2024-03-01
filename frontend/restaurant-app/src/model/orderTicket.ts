export interface OrderTicket {
  id: string;
  createdAt: string;
  status: String; // TODO: make as enum
  totalPrice: number;
  orderItems: OrderTicketItem[];
}

export interface OrderTicketItem {
  id: string;
  orderTicketId: string;
  menuItemId: string;
  quantity: number;
  price: number;
}