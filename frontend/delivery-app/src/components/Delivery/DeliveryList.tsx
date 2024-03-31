import React, {useEffect} from "react";
import {Button, Card, List, Typography} from "antd";
import {useNavigate} from "react-router-dom";

import {usePagination} from "../../hooks/usePagination.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {useQuery} from "@tanstack/react-query";

import styles from "./Delivery.module.css";
import {Delivery} from "../../model/delivery.ts";
import {fetchDeliveries} from "../../client/deliveryApiClient.ts";
import {getAddressAsString} from "../../model/address.ts";


const {Paragraph} = Typography;

interface OrderListProps {
}

const DeliveryList: React.FC<OrderListProps> = ({}: OrderListProps) => {
  const navigate = useNavigate();
  const {pageSize, currentPage, handlePageChange} = usePagination();

  const {
    data: deliveriesPage,
    isPending,
    error,
    refetch,
  } = useQuery<PageableRestApiResponse<Delivery>, Error>({
    queryKey: ["order-deliveries"],
    queryFn: fetchDeliveries.bind(null, currentPage - 1, pageSize),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize]);


  const handleCardClick = (ticketId: string) => {
    navigate(ticketId);
  }

  if (error) {
    return <p>Error: {error.message}</p>;
  }

  return (
      <List
          loading={isPending}
          itemLayout="vertical"
          size="large"
          pagination={{
            position: 'bottom',
            align: 'start',
            pageSize: pageSize,
            current: currentPage,
            total: deliveriesPage?.totalElements,
            onChange: handlePageChange,
            onShowSizeChange: handlePageChange,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
            responsive: true,
          }}
          dataSource={deliveriesPage?.content || []}
          renderItem={delivery => (
              <List.Item>
                <Card
                    hoverable
                    className={styles.card}
                    styles={{body: {padding: '0px 24px 24px 24px'}}}
                    onClick={handleCardClick.bind(null, delivery.id)}
                    title={`Delivery ${delivery.id}`}
                >
                  <div className={styles.cardContent}>
                    <Paragraph>
                      <strong>Last update:</strong> {`${new Date(delivery.lastModifiedAt).toLocaleString()}`}
                    </Paragraph>
                    <Paragraph>
                      <strong>Delivery status:</strong> {`${delivery.deliveryStatus}`}
                    </Paragraph>
                    <Paragraph>
                      <strong>Restaurant name:</strong> {delivery.restaurantName}
                    </Paragraph>
                    <Paragraph>
                      <strong>Restaurant address:</strong> {getAddressAsString(delivery.restaurantAddress)}
                    </Paragraph>
                    <Paragraph>
                      <strong>Delivery address:</strong> {getAddressAsString(delivery.deliveryAddress)}
                    </Paragraph>
                  </div>
                  <Button className={styles.btnPrimary} type="primary"
                          onClick={handleCardClick.bind(null, delivery.id)}>
                    View Details
                  </Button>
                </Card>
              </List.Item>
          )
          }
      />
  );
};

export default DeliveryList;
