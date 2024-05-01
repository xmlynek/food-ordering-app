import React, {useEffect, useState} from "react";
import {Button, Card, Input, Table, Tag, Typography} from "antd";
import {useNavigate, useSearchParams} from "react-router-dom";
import {
  BasicRestaurantRestDTO,
} from "../../model/restApiDto.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {fetchRestaurants} from "../../client/catalogRestaurantsApiClient.ts";
import {useQuery} from "@tanstack/react-query";

import styles from './RestaurantList.module.css';
import {usePagination} from "../../hooks/usePagination.ts";
import {ColumnsType} from "antd/es/table";

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

  const columns: ColumnsType<BasicRestaurantRestDTO> = [
    {
      title: 'Sort by name',
      dataIndex: 'name',
      key: 'name',
      defaultSortOrder: 'ascend',
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (_, restaurant) => (
          <Card
              hoverable
              className={styles.card}
              // onClick={handleCardClick.bind(null, restaurant.id)}
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
            <Button className={styles.btnPrimary} type="primary">
              View Details
            </Button>
          </Card>
      ),
    },
  ];

  const dataSource = restaurantsPage?.content.map(restaurant => ({
    key: restaurant.id,
    ...restaurant,
  })) || [];


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
        <Table
            columns={columns}
            dataSource={dataSource}
            loading={isPending}
            onRow={(record) => ({
              onClick: () => handleCardClick(record.id),
            })}
            pagination={{
              position: ['bottomRight'],
              pageSize: pageSize,
              current: currentPage,
              total: restaurantsPage?.totalElements,
              onChange: handlePageChange,
              showSizeChanger: true,
              showQuickJumper: true,
              showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
              responsive: true,
            }}
        />
      </>
  );
};

export default RestaurantList;