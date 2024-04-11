import {Card, Space, Typography} from "antd";
import React from "react";
import RestaurantList from "../components/Restaurant/RestaurantList.tsx";
import {Outlet} from "react-router-dom";

const {Title} = Typography;

const RestaurantsListPage: React.FC = () => {
  return (
      <Card>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1} style={{marginTop: '10px'}}>Restaurants</Title>
        </ Space>

        <Card title="List of available restaurants" bordered={false}
              style={{marginTop: '10px', boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}}>
          {<RestaurantList/>}
        </Card>
        <Outlet/>
      </Card>
  );
};

export default RestaurantsListPage;