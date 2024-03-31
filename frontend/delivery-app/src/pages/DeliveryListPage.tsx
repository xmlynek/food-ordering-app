import {Card, Space, Typography} from "antd";
import React from "react";
import {Outlet} from "react-router-dom";
import DeliveryList from "../components/Delivery/DeliveryList.tsx";

const {Title} = Typography;

const DeliveryListPage: React.FC = () => {
  return (
      <div style={{padding: '20px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>Orders Delivery</Title>
        </ Space>
        <Card title="List of available deliveries" bordered={false}>
          {<DeliveryList/>}
        </Card>
        <Outlet/>
      </div>
  );
};

export default DeliveryListPage;