import {Card, Layout, Space, Typography} from "antd";
import React from "react";
import {Outlet, useParams} from "react-router-dom";
import RestaurantDetails from "../components/Restaurant/RestaurantDetails.tsx";
import RestaurantLayout from "../components/Restaurant/layout/RestaurantLayout.tsx";
import {useQuery} from "@tanstack/react-query";
import {Restaurant} from "../model/restaurant.ts";
import {fetchRestaurantById} from "../client/restaurantApiClient.ts";

const {Title} = Typography;

const {Content} = Layout;

const RestaurantPage: React.FC = () => {
  const params = useParams();

  const restaurantId = params.id as string

  const {
    data: restaurant,
    error,
    isPending,
  } = useQuery<Restaurant, Error>({
    queryKey: ['restaurantById', restaurantId],
    queryFn: fetchRestaurantById.bind(null, restaurantId)
  });

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