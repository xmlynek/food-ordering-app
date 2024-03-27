import React from "react";
import {Layout, Spin, Typography} from "antd";
import {BasicKitchenTicketRestDTO} from "../model/kitchenTicket.ts";
import KitchenTicketList from "../components/Ticket/KitchenTicketList.tsx";
import {Outlet, useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {fetchKitchenTickets} from "../client/kitchenTicketsApiClient.ts";

const {Title} = Typography;

const KitchenTicketsPage: React.FC = () => {
  const params = useParams()

  const restaurantId = params.id as string;


  const {data: kitchenTickets, isPending, error} = useQuery<BasicKitchenTicketRestDTO[], Error>({
    queryKey: ["kitchen-tickets"],
    queryFn: fetchKitchenTickets.bind(null, restaurantId),
  });

  if (isPending) return <Spin size="large"/>;

  if (error) {
    return <p>Error: {error.message}</p>;
  }


  return (
      <>
        <Outlet/>

        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of kitchen tickets</Title>
        {/*</ Space>*/}
        <KitchenTicketList kitchenTickets={kitchenTickets} isPending={isPending}/>

        {/*<Outlet />*/}
      </>
  );
};

export default KitchenTicketsPage;