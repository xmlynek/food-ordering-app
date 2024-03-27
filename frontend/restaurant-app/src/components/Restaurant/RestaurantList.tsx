import React, {useEffect} from "react";
import {Button, Card, List, Tag, Typography} from "antd";
import {useNavigate} from "react-router-dom";
import {BasicRestaurantRestDto} from "../../model/restaurant.ts";
import styles from './RestaurantList.module.css';
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {usePagination} from "../../hooks/usePagination.ts";
import {fetchRestaurants} from "../../client/restaurantApiClient.ts";
import {useQuery} from "@tanstack/react-query";

const {Title, Paragraph} = Typography;

interface RestaurantListProps {

}

const RestaurantList: React.FC<RestaurantListProps> = ({}: RestaurantListProps) => {
  const navigate = useNavigate();
  const {pageSize, currentPage, handlePageChange} = usePagination();

  const {
    data: restaurantsPage,
    error,
    isPending,
    refetch,
  } = useQuery<PageableRestApiResponse<BasicRestaurantRestDto>, Error>({
    queryKey: ['restaurants', currentPage, pageSize],
    queryFn: fetchRestaurants.bind(null, currentPage - 1, pageSize),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize]);

  const handleViewDetailsButton = (id: string, event: React.MouseEvent<HTMLButtonElement>) => {
    event.stopPropagation();
    navigate(`/restaurants/${id}/menu`);
  }

  const handleCardClick = (id: string) => {
    navigate(`/restaurants/${id}/menu`);
  }

  if (error) return 'An error has occurred: ' + error.message

  return (
      <div className={styles.restaurantList}>
        <List
            loading={isPending}
            grid={{gutter: 16, column: 2, xs: 1, sm: 1, md: 2, lg: 2}}
            pagination={{
              position: 'bottom',
              align: 'start',
              pageSize: pageSize,
              current: currentPage,
              total: restaurantsPage?.totalElements,
              onChange: handlePageChange,
              onShowSizeChange: handlePageChange,
              showSizeChanger: true,
              showQuickJumper: true,
              showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
              responsive: true,
            }}
            dataSource={restaurantsPage?.content || []}
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