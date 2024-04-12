import {Card, Space, Typography} from "antd";
import React from "react";
import {Outlet} from "react-router-dom";
import DeliveryList from "../components/Delivery/DeliveryList.tsx";
import {fetchCourierDeliveryHistory} from "../client/deliveryApiClient.ts";

const {Title} = Typography;

const DeliveryHistoryPage: React.FC = () => {
  return (
      <Card>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1} style={{marginTop: '10px'}}>Your Delivery History</Title>
        </ Space>
        <Card title="List of you assigned deliveries" bordered={false}
              style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px', marginTop: '15px'}}>
          {<DeliveryList queryKey={["order-delivery-history"]} queryFn={fetchCourierDeliveryHistory} />}
        </Card>
        <Outlet/>
      </Card>
  );
};

export default DeliveryHistoryPage;