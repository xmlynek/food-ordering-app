import {Card, Space, Typography} from "antd";
import React from "react";
import {useParams} from "react-router-dom";
import RestaurantDetails from "../components/Restaurant/RestaurantDetails.tsx";
import {useQuery} from "@tanstack/react-query";
import RestaurantMenuList from "../components/RestaurantMenu/RestaurantMenuList.tsx";
import {fetchRestaurantById} from "../client/catalogRestaurantsApiClient.ts";
import {FullRestaurantRestDTO} from "../model/restApiDto.ts";

const {Title} = Typography;


const RestaurantPage: React.FC = () => {
  const params = useParams();

  const restaurantId = params.id as string;

  const {
    data: restaurant,
    error,
    isPending,
  } = useQuery<FullRestaurantRestDTO, Error>({
    queryKey: ['restaurantById', restaurantId],
    queryFn: fetchRestaurantById.bind(null, restaurantId),
  })

  if (error) return 'An error has occurred: ' + error.message
  if (isPending) return 'Loading...'

  return (
      <Card>
        <div style={{marginBottom: '24px', textAlign: 'center'}}>
          <Title level={1} style={{marginTop: '10px'}}>{restaurant.name}</Title>
        </div>

        <Space direction="vertical" size="large" style={{width: '100%'}}>
          <RestaurantDetails restaurant={restaurant}/>
          <RestaurantMenuList/>
        </Space>
      </Card>
  );
};

export default RestaurantPage;