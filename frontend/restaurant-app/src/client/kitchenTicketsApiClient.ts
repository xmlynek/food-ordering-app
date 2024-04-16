import {BasicKitchenTicketRestDTO, KitchenTicketDetailsRestDTO} from "../model/kitchenTicket.ts";
import {axiosInstance} from "./axiosInstance.ts";
import {PageableRestApiResponse} from "../model/pageable.ts";

export const fetchKitchenTickets = async (restaurantId: string, page: number = 0, size: number = 10, ticketStatus: string = ""): Promise<PageableRestApiResponse<BasicKitchenTicketRestDTO>> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_KITCHEN_SERVICE_PATH}/${restaurantId}/tickets?page=${page}&size=${size}&ticketStatus=${ticketStatus}`);
  return response.data;
};

export const fetchKitchenTicketDetailsById = async (restaurantId: string, ticketId: string): Promise<KitchenTicketDetailsRestDTO> => {
  const response = await axiosInstance.get(`${window.envVars.REACT_KITCHEN_SERVICE_PATH}/${restaurantId}/tickets/${ticketId}`);
  return response.data;
};

export const completeKitchenTicket = async (restaurantId: string, ticketId: string): Promise<void> => {
  const response = await axiosInstance.post(`${window.envVars.REACT_KITCHEN_SERVICE_PATH}/${restaurantId}/tickets/${ticketId}/complete`);
  return response.data;
};