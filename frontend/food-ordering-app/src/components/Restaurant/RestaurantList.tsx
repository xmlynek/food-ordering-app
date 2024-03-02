import React from "react";
import {Avatar, List} from "antd";
import {useNavigate} from "react-router-dom";
import {Restaurant} from "../../model/restaurant.ts";


interface RestaurantListProps {
  restaurants: Restaurant[];
  isPending: boolean;
}

const RestaurantList: React.FC<RestaurantListProps> = ({
                                                         restaurants,
                                                         isPending
                                                       }: RestaurantListProps) => {
  const navigate = useNavigate();


  return (
      <List
          loading={isPending}
          itemLayout="horizontal"
          pagination={{position: 'bottom', align: 'start', pageSize: 10}}
          dataSource={restaurants}
          renderItem={item => (
              <List.Item onClick={() => navigate(item.id)}>
                <List.Item.Meta
                    avatar={<Avatar/>}
                    title={
                      <p>{item.name}</p>} // Replace href with a link to the restaurant detail page
                    description={item?.isAvailable ? 'Available' : 'Not available'}
                />
              </List.Item>
          )}
      />
  );
};

export default RestaurantList;