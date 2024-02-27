interface Restaurant {
  id: string;
  name: string;
  isAvailable: boolean;
  menuItems: MenuItem[];
}

interface MenuItem {
  id: string;
  name: string;
  description: string;
  price: number;
}