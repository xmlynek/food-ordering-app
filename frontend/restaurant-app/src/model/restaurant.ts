export interface Restaurant {
  id: string;
  name: string;
  isAvailable: boolean;
  menuItems: MenuItem[];
}

export interface MenuItem {
  id: string;
  name: string;
  description: string;
  price: number;
}