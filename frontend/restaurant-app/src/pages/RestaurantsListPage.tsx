import {Button, Card, Space, Typography} from "antd";
import React from "react";
import RestaurantList from "../components/Restaurant/RestaurantList.tsx";
import {Outlet, useNavigate} from "react-router-dom";

const {Title} = Typography;

const RestaurantsListPage: React.FC = () => {
  const navigate = useNavigate();


  return (
      <Card style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center', marginBottom: '10px'}}>
          <Title level={1} style={{marginTop: '10px'}}>Restaurants</Title>
        </ Space>

        <Card title="List of your restaurants" bordered={false}
              style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}} extra={
                <Button type="primary" onClick={() => {
                navigate("add")
              }}>
          Create Restaurant
        </Button>
        }>
        {<RestaurantList/>}
      </Card>
  <Outlet/>
</Card>
)
  ;
};

export default RestaurantsListPage;