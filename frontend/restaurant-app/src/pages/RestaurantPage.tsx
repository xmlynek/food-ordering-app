import {Card, Layout, Space, Typography} from "antd";
import React from "react";
import {Outlet} from "react-router-dom";
import RestaurantDetails from "../components/Restaurant/RestaurantDetails.tsx";
import RestaurantLayout from "../components/Restaurant/layout/RestaurantLayout.tsx";

const {Title} = Typography;

const {Content} = Layout;

const RestaurantPage: React.FC = () => {

  const data: Restaurant = {
    id: "1",
    name: "Italian Bistro",
    description: "Authentic Italian cuisine with a modern twist."
  }

  return (
      <Content style={{padding: '0 48px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>{data.name}</Title>
        </Space>

        <Card title="Restaurant Details">
          <RestaurantDetails restaurant={data}/>
        </Card>

        <RestaurantLayout>
          <Outlet/>
        </RestaurantLayout>
      </Content>
  );
};

export default RestaurantPage;