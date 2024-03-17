import {Card, Layout, Space, Typography} from "antd";
import React from "react";
import {useParams} from "react-router-dom";
import RestaurantDetails from "../components/Restaurant/RestaurantDetails.tsx";
import {useQuery} from "@tanstack/react-query";
import {Restaurant} from "../model/restaurant.ts";
import RestaurantMenuList from "../components/RestaurantMenu/RestaurantMenuList.tsx";
import {fetchRestaurantById} from "../client/restaurantApiClient.ts";

const {Title} = Typography;

const {Content} = Layout;

const RestaurantPage: React.FC = () => {
  const params = useParams();

  const restaurantId = params.id as string;

  const {
    data: restaurant,
    error,
    isPending,
  } = useQuery<Restaurant, Error>({
    queryKey: ['restaurantById', restaurantId],
    queryFn: fetchRestaurantById.bind(null, restaurantId),
  })

  if (error) return 'An error has occurred: ' + error.message
  if (isPending) return 'Loading...'

  return (
      <Content style={{padding: '24px'}}>
        <div style={{marginBottom: '24px', textAlign: 'center'}}>
          <Title level={1}>{restaurant.name}</Title>
        </div>

        <Space direction="vertical" size="large" style={{width: '100%'}}>
          <Card title="Restaurant Details" bordered={false}
                style={{boxShadow: '0 4px 8px rgba(0,0,0,0.1)'}}>
            <RestaurantDetails restaurant={restaurant}/>
          </Card>

          <Card title="Available Menu Products" bordered={false}
                style={{boxShadow: '0 4px 8px rgba(0,0,0,0.1)'}}>
            <RestaurantMenuList/>
          </Card>
        </Space>
      </Content>
  );
};

export default RestaurantPage;