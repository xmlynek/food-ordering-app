import {Descriptions} from "antd";
import React from "react";
import {FullRestaurantRestDTO} from "../../model/restApiDto.ts";


interface RestaurantProps {
  restaurant: FullRestaurantRestDTO;
}

const RestaurantDetails: React.FC<RestaurantProps> = ({restaurant}: RestaurantProps) => {

  return (
      <Descriptions column={1} bordered>
        <Descriptions.Item label="ID">{restaurant.id}</Descriptions.Item>
        <Descriptions.Item label="Name">{restaurant.name}</Descriptions.Item>
        <Descriptions.Item
            label="IsAvailable">{restaurant.isAvailable ? 'Available' : 'Not available'}</Descriptions.Item>
      </Descriptions>
  );
};

export default RestaurantDetails;
