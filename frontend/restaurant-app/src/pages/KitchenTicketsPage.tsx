import React from "react";
import {Card, Typography} from "antd";
import KitchenTicketList from "../components/Ticket/KitchenTicketList.tsx";
import {Outlet} from "react-router-dom";

const {Title} = Typography;

const KitchenTicketsPage: React.FC = () => {

  return (
      <>
        <Outlet/>
        <Card style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}}>
          <Title style={{marginTop: 0}} level={2}>List of kitchen tickets</Title>
          <KitchenTicketList/>
        </Card>
      </>
  );
};

export default KitchenTicketsPage;