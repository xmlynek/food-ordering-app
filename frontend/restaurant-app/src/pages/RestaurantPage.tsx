import {Card, Layout, Space, Typography} from "antd";
import React from "react";
import {Outlet, useParams} from "react-router-dom";
import RestaurantDetails from "../components/Restaurant/RestaurantDetails.tsx";
import RestaurantLayout from "../components/Restaurant/layout/RestaurantLayout.tsx";
import {useQuery} from "@tanstack/react-query";

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
      <Content style={{padding: '0 48px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>{restaurant.name}</Title>
        </Space>

        <Card title="Restaurant Details">
          <RestaurantDetails restaurant={restaurant}/>
        </Card>

        <RestaurantLayout>
          <Outlet/>
        </RestaurantLayout>
      </Content>
  );
};

export default RestaurantPage;