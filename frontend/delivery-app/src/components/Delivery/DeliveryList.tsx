import React, {useEffect} from "react";
import {Button, Card, Divider, List, Space, Typography} from "antd";
import {useNavigate} from "react-router-dom";

import {usePagination} from "../../hooks/usePagination.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {useQuery} from "@tanstack/react-query";

import styles from "./Delivery.module.css";
import {Delivery} from "../../model/delivery.ts";
import {getAddressAsString} from "../../model/address.ts";
import useSortableData from "../../hooks/useSortableData.ts";
import {DownOutlined, UpOutlined} from "@ant-design/icons";


const {Paragraph} = Typography;

interface OrderListProps {
  queryKey: Array<any>;
  queryFn: (page: number, size: number) => Promise<PageableRestApiResponse<Delivery>>;
}

const DeliveryList: React.FC<OrderListProps> = ({queryKey, queryFn}: OrderListProps) => {
  const navigate = useNavigate();
  const {pageSize, currentPage, handlePageChange} = usePagination();

  const {
    data: deliveriesPage,
    isPending,
    error,
    refetch,
  } = useQuery<PageableRestApiResponse<Delivery>, Error>({
    queryKey: queryKey,
    queryFn: queryFn.bind(null, currentPage - 1, pageSize),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize]);

  const {
    items: sortedDeliveries,
    requestSort,
    sortConfig,
    clearSort
  } = useSortableData<Delivery>(deliveriesPage?.content || []);


  const handleCardClick = (ticketId: string) => {
    navigate(ticketId);
  }

  if (error) {
    return <p>Error: {error.message}</p>;
  }

  return (
      <>
        <Space style={{marginBottom: 16}}>
          <Button onClick={() => requestSort("lastModifiedAt")}>
            Sort by Last update{sortConfig.key === "lastModifiedAt" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={() => requestSort("kitchenTicketStatus")}>
            Sort by Kitchen status {sortConfig.key === "kitchenTicketStatus" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={() => requestSort("deliveryStatus")}>
            Sort by Delivery status {sortConfig.key === "deliveryStatus" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={() => requestSort("restaurantName")}>
            Sort by Restaurant name {sortConfig.key === "restaurantName" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={clearSort}>Clear Sort</Button>
          <Divider/>
        </Space>
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
            dataSource={sortedDeliveries}
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
                        <strong>Last
                          update:</strong> {`${new Date(delivery.lastModifiedAt).toLocaleString()}`}
                      </Paragraph>
                      <Paragraph>
                        <strong>Kitchen status:</strong> {`${delivery.kitchenTicketStatus}`}
                      </Paragraph>
                      <Paragraph>
                        <strong>Delivery status:</strong> {`${delivery.deliveryStatus}`}
                      </Paragraph>
                      <Paragraph>
                        <strong>Restaurant name:</strong> {delivery.restaurantName}
                      </Paragraph>
                      <Paragraph>
                        <strong>Restaurant
                          address:</strong> {getAddressAsString(delivery.restaurantAddress)}
                      </Paragraph>
                      <Paragraph>
                        <strong>Delivery
                          address:</strong> {getAddressAsString(delivery.deliveryAddress)}
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
      </>

  );
};

export default DeliveryList;
