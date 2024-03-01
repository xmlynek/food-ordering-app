import React from "react";
import {Layout, Typography} from "antd";
import {OrderTicket} from "../model/orderTicket.ts";
import OrderTicketList from "../components/Order/OrderTicketList.tsx";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";

const {Title} = Typography;
const {Content} = Layout;

const OrderTicketsPage: React.FC = () => {
  const params = useParams()

  const fetchOrderTickets = async (): Promise<OrderTicket[]> => {
    const response = await axios.get(`http://localhost:8085/api/restaurants/${params.id}/orderTickets`);
    return response.data;
  };

  const {data: orderTickets, isPending, error} = useQuery<OrderTicket[], Error>({
    queryKey: ["order-tickets"],
    queryFn: fetchOrderTickets
  });

  // if (isPending) {
  //   return <p>Loading...</p>;
  // }

  if (error) {
    return <p>Error: {error.message}</p>;
  }


  return (
      <Content>
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of orders</Title>
        {/*</ Space>*/}
        <OrderTicketList orderTickets={orderTickets} isPending={isPending}/>

      </Content>
  );
};

export default OrderTicketsPage;