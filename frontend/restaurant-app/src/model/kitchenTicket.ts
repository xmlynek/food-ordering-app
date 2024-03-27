export interface BasicKitchenTicketRestDTO {
  id: string;
  createdAt: string;
  status: String; // TODO: make as enum
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