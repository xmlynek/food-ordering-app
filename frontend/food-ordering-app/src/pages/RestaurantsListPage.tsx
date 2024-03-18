import {Card, Space, Typography} from "antd";
import React from "react";
import RestaurantList from "../components/Restaurant/RestaurantList.tsx";
import {Outlet} from "react-router-dom";

const {Title} = Typography;

const RestaurantsListPage: React.FC = () => {
  return (
      <div style={{padding: '20px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>Restaurants</Title>
        </ Space>
        {/*{error && 'An error has occurred: ' + error.message}*/}
        <Card title="List of available restaurants" bordered={false}>
          {<RestaurantList />}
        </Card>
        <Outlet/>
      </div>
  );
};

export default RestaurantsListPage;