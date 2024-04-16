import {Card, Descriptions, Tag} from "antd";
import React from "react";
import {FullRestaurantRestDTO} from "../../model/restApiDto.ts";


interface RestaurantProps {
  restaurant: FullRestaurantRestDTO;
}

const RestaurantDetails: React.FC<RestaurantProps> = ({restaurant}: RestaurantProps) => {
  return (
      <Card title="Restaurant Details" bordered={false} style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}}>
        <Descriptions bordered layout="vertical" column={{
          xxl: 2,
          xl: 2,
          lg: 2,
          md: 2,
          sm: 1,
          xs: 1,
        }}
        >
          <Descriptions.Item label="Name">{restaurant.name}</Descriptions.Item>
          <Descriptions.Item label="Description"
                             style={{textAlign: "justify"}}>{restaurant.description}</Descriptions.Item>
          <Descriptions.Item label="Availability">
            {restaurant.isAvailable ? (
                <Tag color="green">Available</Tag>
            ) : (
                <Tag color="red">Not available</Tag>
            )}
          </Descriptions.Item>
          <Descriptions.Item label="Address">
            {`${restaurant.address.street}, ${restaurant.address.city}, ${restaurant.address.postalCode}, ${restaurant.address.country}`}
          </Descriptions.Item>
        </Descriptions>
      </Card>
  );
};

export default RestaurantDetails;
