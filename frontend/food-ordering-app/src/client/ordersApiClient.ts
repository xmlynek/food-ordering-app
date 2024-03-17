import axios from "axios";


export const postOrder = async (orderData) => {
  const response = await axios.post('http://localhost:8080/api/orders', orderData);
  return response.data;
}