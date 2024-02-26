import {Descriptions} from "antd";
import React from "react";


interface RestaurantProps {
  restaurant: Restaurant;
}

const RestaurantDetails: React.FC<RestaurantProps> = ({restaurant}: RestaurantProps) => {

  return (
      <Descriptions column={2}>
        <Descriptions.Item label="ID">{restaurant.id}</Descriptions.Item>
        <Descriptions.Item label="Name">{restaurant.name}</Descriptions.Item>
        <Descriptions.Item label="Description">{restaurant.description}</Descriptions.Item>
      </Descriptions>
  );
};

export default RestaurantDetails;
