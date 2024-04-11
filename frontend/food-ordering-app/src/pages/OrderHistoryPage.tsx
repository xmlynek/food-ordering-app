import {Card, Space, Typography} from "antd";
import React from "react";
import {Outlet} from "react-router-dom";
import OrderHistoryList from "../components/Order/OrderHistoryList.tsx";

const {Title} = Typography;

const OrderHistoryPage: React.FC = () => {
  return (
      <Card>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1} style={{marginTop: '10px'}}>Order history</Title>
        </ Space>
        <Card title="List of your orders" bordered={false}
              style={{marginTop: '10px', boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}}>
          {<OrderHistoryList/>}
        </Card>
        <Outlet/>
      </Card>
  );
};

export default OrderHistoryPage;