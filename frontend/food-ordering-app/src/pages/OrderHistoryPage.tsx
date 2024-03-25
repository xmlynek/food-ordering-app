import {Card, Space, Typography} from "antd";
import React from "react";
import {Outlet} from "react-router-dom";
import OrderHistoryList from "../components/Order/OrderHistoryList.tsx";

const {Title} = Typography;

const OrderHistoryPage: React.FC = () => {
  return (
      <div style={{padding: '20px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>Order history</Title>
        </ Space>
        <Card title="List of your orders" bordered={false}>
          {<OrderHistoryList />}
        </Card>
        <Outlet/>
      </div>
  );
};

export default OrderHistoryPage;