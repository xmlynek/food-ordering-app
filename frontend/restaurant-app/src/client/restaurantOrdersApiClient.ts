import {OrderTicket} from "../model/orderTicket.ts";
import {axiosInstance} from "./axiosInstance.ts";

export const fetchOrderTickets = async (restaurantId: string): Promise<OrderTicket[]> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_RESTAURANT_SERVICE_PATH}/${restaurantId}/orderTickets`);
  return response.data;
};