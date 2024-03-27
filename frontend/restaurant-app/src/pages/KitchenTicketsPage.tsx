import React from "react";
import {Typography} from "antd";
import KitchenTicketList from "../components/Ticket/KitchenTicketList.tsx";
import {Outlet} from "react-router-dom";

const {Title} = Typography;

const KitchenTicketsPage: React.FC = () => {

  return (
      <>
        <Outlet/>
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of kitchen tickets</Title>
        {/*</ Space>*/}
        <KitchenTicketList/>
      </>
  );
};

export default KitchenTicketsPage;