import React, {useEffect} from "react";
import {Button, Card, List, Typography} from "antd";
import {useNavigate} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {fetchOrderHistory} from "../../client/ordersApiClient.ts";
import keycloak from "../../keycloak/keycloak.ts";
import {OrderHistoryDto} from "../../model/order.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {usePagination} from "../../hooks/usePagination.ts";

import styles from './OrderHistoryList.module.css';

const {Paragraph} = Typography;

const OrderHistoryList: React.FC = () => {

  const {pageSize, currentPage, handlePageChange} = usePagination();
  const navigate = useNavigate();

  const {
    data: orderHistoryPage,
    error,
    isPending,
    refetch,
  } = useQuery<PageableRestApiResponse<OrderHistoryDto>, Error>({
    queryKey: ['orderHistory', currentPage, pageSize],
    queryFn: fetchOrderHistory.bind(null, keycloak.subject || '', currentPage - 1, pageSize),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize]);


  const handleCardClick = (orderHistoryId: string) => {
    navigate(`${orderHistoryId}`);
  }

  if (error) return 'An error has occurred: ' + error.message

  return (
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
            total: orderHistoryPage?.totalElements,
            onChange: handlePageChange,
            onShowSizeChange: handlePageChange,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
            responsive: true,
          }}
          dataSource={orderHistoryPage?.content || []}
          renderItem={orderHistoryItem => (
              <List.Item>
                <Card
                    hoverable
                    className={styles.card}
                    onClick={handleCardClick.bind(null, orderHistoryItem.id)}
                    title={`Order ${orderHistoryItem.id}`}
                >
                  <div className={styles.cardContent}>
                    <Paragraph>
                      <strong>Restaurant ID:</strong> {orderHistoryItem.restaurantId}
                    </Paragraph>
                    <Paragraph>
                      <strong>Created:</strong> {`${new Date(orderHistoryItem.createdAt).toLocaleString()}`}
                    </Paragraph>
                    <Paragraph>
                      <strong>Order status:</strong> {`${orderHistoryItem.orderStatus}`}
                    </Paragraph>
                    <Paragraph>
                      <strong>Total:</strong> {`${orderHistoryItem.totalPrice.toFixed(2)} €`}
                    </Paragraph>
                  </div>
                  <Button className={styles.btnPrimary} type="primary"
                          onClick={handleCardClick.bind(null, orderHistoryItem.id)}>
                    View Details
                  </Button>
                </Card>
              </List.Item>
          )
          }
      />
  );
};

export default OrderHistoryList;