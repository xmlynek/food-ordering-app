import React from "react";
import {Button, Card, List, Tag, Typography} from "antd";
import {useNavigate} from "react-router-dom";
import {BasicRestaurantRestDto} from "../../model/restaurant.ts";
import styles from './RestaurantList.module.css';

const {Title, Paragraph} = Typography;

interface RestaurantListProps {
  restaurants: BasicRestaurantRestDto[] | undefined;
  isPending: boolean;
}

const RestaurantList: React.FC<RestaurantListProps> = ({
                                                         restaurants,
                                                         isPending
                                                       }: RestaurantListProps) => {
  const navigate = useNavigate();

  const handleViewDetailsButton = (id: string, event: React.MouseEvent<HTMLButtonElement>) => {
    event.stopPropagation();
    navigate(`/restaurants/${id}/menu`);
  }

  const handleCardClick = (id: string) => {
    navigate(`/restaurants/${id}/menu`);
  }


  return (
      <div className={styles.restaurantList}>
        <List
            loading={isPending}
            grid={{gutter: 16, column: 2, xs: 1, sm: 1, md: 2, lg: 2}}
            pagination={{position: 'bottom', pageSize: 10}}
            dataSource={restaurants}
            renderItem={(restaurant) => (
                <List.Item>
                  <Card
                      hoverable
                      className={styles.card}
                      onClick={handleCardClick.bind(null, restaurant.id)}
                      title={
                        <div className={styles.cardTitleContainer}>
                          <Title level={4}>{restaurant.name}</Title>
                          <span>{restaurant.isAvailable ? (<Tag color="green">Available</Tag>) : (
                              <Tag color="red">Not available</Tag>)}
                          </span>
                        </div>
                      }
                  >
                    <div className={styles.cardContent}>
                      <Paragraph ellipsis={{rows: 3}}>
                        <strong>Description:</strong> {restaurant.description}
                      </Paragraph>
                      <Paragraph>
                        <strong>Address:</strong> {`${restaurant.address.street}, ${restaurant.address.city}, ${restaurant.address.postalCode}, ${restaurant.address.country}`}
                      </Paragraph>
                    </div>
                    <Button className={styles.btnPrimary} type="primary"
                            onClick={handleViewDetailsButton.bind(null, restaurant.id)}>
                      View Details
                    </Button>
                  </Card>
                </List.Item>
            )}
        />
      </div>
  );
};

export default RestaurantList;