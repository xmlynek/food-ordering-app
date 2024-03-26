import {createContext, useState, ReactNode, useEffect, useMemo} from 'react';
import {
  clearBasketFromLocalStorage,
  getBasketFromLocalStorage,
  saveBasketToLocalStorage
} from "../utils/localStorageUtils.ts";
import {message} from "antd";

interface BasketContextType {
  basket: BasketItem[];
  totalItems: number;
  calculateTotalPrice: () => number;
  addToBasket: (item: BasketItem) => void;
  updateQuantity: (itemId: string, quantity: number) => void;
  removeFromBasket: (itemId: string) => void;
  clearBasket: () => void;
}

export const BasketContext = createContext<BasketContextType>({
  basket: [],
  totalItems: 0,
  calculateTotalPrice: () => 0,
  addToBasket: () => {
  },
  updateQuantity: () => {
  },
  removeFromBasket: () => {
  },
  clearBasket: () => {
  },
});

interface BasketProviderProps {
  children: ReactNode;
}


export const BasketProvider = ({children}: BasketProviderProps) => {
  const [basket, setBasket] = useState<BasketItem[]>(getBasketFromLocalStorage());
  const [totalItems, setTotalItems] = useState<number>(0);

  useEffect(() => {
    saveBasketToLocalStorage(basket);
    const total = basket.reduce((acc, item) => acc + item.quantity, 0);
    setTotalItems(total);
  }, [basket]);

  const addToBasket = async (newItem: BasketItem) => {
    setBasket((prevBasket) => {
      const differentRestaurantExists = prevBasket.some(item => item.restaurantId !== newItem.restaurantId);

      if (differentRestaurantExists) {
        alert("Your basket contains items from a different restaurant. Please clear your basket to add items from this restaurant.");
        return prevBasket;
      } else {
        const existingItemIndex = prevBasket.findIndex(item => item.id === newItem.id);

        if (existingItemIndex >= 0) {
          const updatedBasket = [...prevBasket];
          updatedBasket[existingItemIndex] = {
            ...updatedBasket[existingItemIndex],
            quantity: updatedBasket[existingItemIndex].quantity + newItem.quantity,
          };
          message.success('Item added to basket');
          return updatedBasket;
        } else {
          message.success('Item added to basket');
          return [...prevBasket, newItem];
        }
      }
    });
  };

  const removeFromBasket = (itemId: string) => {
    setBasket(prev => prev.filter(item => item.id !== itemId));
  };

  const updateQuantity = (itemId: string, quantity: number) => {
    setBasket(prev =>
        prev.map(item => item.id === itemId ? {...item, quantity: Math.max(quantity, 1)} : item)
    );
  };

  const calculateTotalPrice = (): number => {
    return basket.reduce((total, item) => total + item.price * item.quantity, 0);
  };

  const clearBasket = () => {
    setBasket([]);
    clearBasketFromLocalStorage();
  }

  const value = useMemo(() => ({
    basket,
    calculateTotalPrice,
    addToBasket,
    removeFromBasket,
    updateQuantity,
    clearBasket,
  }), [basket]);

  return (
      <BasketContext.Provider
          value={{...value, totalItems}}>
        {children}
      </BasketContext.Provider>
  );
};
