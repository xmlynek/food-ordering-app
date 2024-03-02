import {Card, Col, Layout, Row, Space, Typography} from "antd";
import React from "react";
import {useParams} from "react-router-dom";
import RestaurantDetails from "../components/Restaurant/RestaurantDetails.tsx";
import {useQuery} from "@tanstack/react-query";
import {Restaurant} from "../model/restaurant.ts";
import RestaurantMenuList from "../components/RestaurantMenu/RestaurantMenuList.tsx";

const {Title} = Typography;

const {Content} = Layout;

const RestaurantPage: React.FC = () => {
  const params = useParams();

  const fetchRestaurantById = async (): Promise<Restaurant> => {
    const response = await fetch(`http://localhost:8085/api/restaurants/${params.id}`);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  };

  const {
    data: restaurant,
    error,
    isPending,
  } = useQuery<Restaurant, Error>({queryKey: ['restaurantById'], queryFn: fetchRestaurantById});

  if (error) return 'An error has occurred: ' + error.message
  if (isPending) return 'Loading...'

  return (
      <Content style={{ padding: '24px' }}>
        <div style={{ marginBottom: '24px', textAlign: 'center' }}>
          <Title level={1}>{restaurant.name}</Title>
        </div>

        <Space direction="vertical" size="large" style={{ width: '100%' }}>
          <Card title="Restaurant Details" bordered={false} style={{ boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}>
            <RestaurantDetails restaurant={restaurant}/>
          </Card>

          <Card title="Available Menu Products" bordered={false} style={{ boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}>
            <RestaurantMenuList />
          </Card>
        </Space>
      </Content>
  );
};

export default RestaurantPage;