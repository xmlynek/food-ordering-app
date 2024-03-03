import {createContext, useState, ReactNode, useEffect} from 'react';
import {message} from "antd";

interface BasketContextType {
  basket: BasketItem[];
  calculateTotalPrice: () => number;
  addToBasket: (item: BasketItem) => void;
  updateQuantity: (itemId: string, quantity: number) => void;
  removeFromBasket: (itemId: string) => void;
}

export const BasketContext = createContext<BasketContextType>({
  basket: [],
  calculateTotalPrice: () => 0,
  addToBasket: () => {
  },
  updateQuantity: () => {
  },
  removeFromBasket: () => {
  },
});

interface BasketProviderProps {
  children: ReactNode;
}

const getBasketFromLocalStorage = () => {
  const storedBasket = localStorage.getItem('basket');
  return storedBasket ? JSON.parse(storedBasket) : [];
};

const saveBasketToLocalStorage = (basket: BasketItem[]) => {
  localStorage.setItem('basket', JSON.stringify(basket));
};


export const BasketProvider = ({children}: BasketProviderProps) => {
  const [basket, setBasket] = useState<BasketItem[]>(getBasketFromLocalStorage());

  useEffect(() => {
    // Update localStorage whenever the basket changes
    saveBasketToLocalStorage(basket);
  }, [basket]);

  const addToBasket = async (newItem: BasketItem) => {
    setBasket((prevBasket) => {
      // Check if the basket contains items from another restaurant
      const differentRestaurantExists = prevBasket.some(item => item.restaurantId !== newItem.restaurantId);

      if (differentRestaurantExists) {
        // Option 1: Alert the user and don't add the item
        alert("Your basket contains items from a different restaurant. Please clear your basket to add items from this restaurant.");
        return prevBasket;
      } else {
        // Proceed to check if the item exists and update quantity or add as new
        const existingItemIndex = prevBasket.findIndex(item => item.id === newItem.id);

        if (existingItemIndex >= 0) {
          // Update quantity
          const updatedBasket = [...prevBasket];
          updatedBasket[existingItemIndex] = {
            ...updatedBasket[existingItemIndex],
            quantity: updatedBasket[existingItemIndex].quantity + newItem.quantity,
          };
          message.success('Item added to basket');
          return updatedBasket;
        } else {
          // Add new item
          return [...prevBasket, newItem];
        }
      }
    });
  };

  const removeFromBasket = async (itemId: string) => {
    setBasket(prev => prev.filter(item => item.id !== itemId));
  };

  const updateQuantity = async (itemId: string, quantity: number) => {
    setBasket(prev =>
        prev.map(item => item.id === itemId ? {...item, quantity: Math.max(quantity, 1)} : item)
    );
  };

  const calculateTotalPrice = () => {
    return basket.reduce((total, item) => total + item.price * item.quantity, 0);
  };

  return (
      <BasketContext.Provider
          value={{basket, calculateTotalPrice, addToBasket, removeFromBasket, updateQuantity}}>
        {children}
      </BasketContext.Provider>
  );
};
