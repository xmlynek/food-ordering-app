import {Descriptions} from "antd";
import React from "react";
import {Restaurant} from "../../model/restaurant.ts";


interface RestaurantProps {
  restaurant: Restaurant;
}

const RestaurantDetails: React.FC<RestaurantProps> = ({restaurant}: RestaurantProps) => {

  return (
      <Descriptions column={2}>
        <Descriptions.Item label="ID">{restaurant.id}</Descriptions.Item>
        <Descriptions.Item label="Name">{restaurant.name}</Descriptions.Item>
        <Descriptions.Item
            label="IsAvailable">{restaurant.isAvailable ? 'Available' : 'Not available'}</Descriptions.Item>
      </Descriptions>
  );
};

export default RestaurantDetails;
