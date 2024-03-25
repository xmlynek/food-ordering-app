import React, {useEffect, useState} from "react";
import {Button, Card, Input, List, Tag, Typography} from "antd";
import {useNavigate, useSearchParams} from "react-router-dom";
import {
  BasicRestaurantRestDTO,
} from "../../model/restApiDto.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {fetchRestaurants} from "../../client/catalogRestaurantsApiClient.ts";
import {useQuery} from "@tanstack/react-query";

import styles from './RestaurantList.module.css';
import {usePagination} from "../../hooks/usePagination.ts";

const {Title, Paragraph} = Typography;


const RestaurantList: React.FC = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();
  const {pageSize, currentPage, handlePageChange} = usePagination();
  const [searchTerm, setSearchTerm] = useState(searchParams.get('searchValue') || '');

  const {
    data: restaurantsPage,
    error,
    isPending,
    refetch,
  } = useQuery<PageableRestApiResponse<BasicRestaurantRestDTO>, Error>({
    queryKey: ['restaurants', currentPage, pageSize],
    queryFn: fetchRestaurants.bind(null, currentPage - 1, pageSize, searchTerm),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize, searchTerm]);

  const handleOnSearch = (searchValue: string) => {
    setSearchTerm(() => searchValue);
    setSearchParams((prev) => {
      const updatedParams = new URLSearchParams(prev);
      if (searchValue) {
        updatedParams.set('searchValue', searchValue);
      } else {
        updatedParams.delete('searchValue');
      }
      return updatedParams;
    });
  }

  const handleCardClick = (restaurantId: string) => {
    navigate(`${restaurantId}`);
  }

  if (error) return 'An error has occurred: ' + error.message

  return (
      <>
        <Input.Search
            placeholder="Search restaurants..."
            defaultValue={searchTerm}
            allowClear
            enterButton
            onSearch={handleOnSearch}
            style={{marginBottom: 20}}
        />
        <List
            loading={isPending}
            itemLayout="horizontal"
            grid={{gutter: 16, column: 1}}
            size="large"
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
            renderItem={restaurant => (
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
                      <Paragraph className={styles.paragraphJustify} ellipsis={{rows: 3}}>
                        <strong>Description:</strong> {restaurant.description}
                      </Paragraph>
                      <Paragraph>
                        <strong>Address:</strong> {`${restaurant.address.street}, ${restaurant.address.city}, ${restaurant.address.postalCode}, ${restaurant.address.country}`}
                      </Paragraph>
                    </div>
                    <Button className={styles.btnPrimary} type="primary"
                            onClick={handleCardClick.bind(null, restaurant.id)}>
                      View Details
                    </Button>
                  </Card>
                </List.Item>
            )
            }
        />
      </>
  );
};

export default RestaurantList;