import {Card, Space, Typography} from "antd";
import React from "react";
import {Outlet} from "react-router-dom";
import DeliveryList from "../components/Delivery/DeliveryList.tsx";
import {fetchDeliveries} from "../client/deliveryApiClient.ts";

const {Title} = Typography;

const DeliveryListPage: React.FC = () => {
  return (
      <Card>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1} style={{marginTop: '10px'}}>Delivery Orders</Title>
        </ Space>
        <Card title="List of available deliveries" bordered={false}
              style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px', marginTop: '15px'}}>
          {<DeliveryList queryKey={['order-deliveries']} queryFn={fetchDeliveries}/>}
        </Card>
        <Outlet/>
      </Card>
  );
};

export default DeliveryListPage;