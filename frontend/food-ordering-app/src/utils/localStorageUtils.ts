const BASKET_KEY = 'basket';

export const getBasketFromLocalStorage = () => {
  const storedBasket = localStorage.getItem(BASKET_KEY);
  return storedBasket ? JSON.parse(storedBasket) : [];
};

export const saveBasketToLocalStorage = (basket: BasketItem[]) => {
  localStorage.setItem(BASKET_KEY, JSON.stringify(basket));
};

export const clearBasketFromLocalStorage = () => {
  localStorage.removeItem(BASKET_KEY);
};