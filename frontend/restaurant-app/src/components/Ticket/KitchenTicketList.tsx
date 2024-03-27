import React, {useEffect} from "react";
import {Button, Card, List, Typography} from "antd";
import {BasicKitchenTicketRestDTO} from "../../model/kitchenTicket.ts";
import {useNavigate, useParams} from "react-router-dom";

import {usePagination} from "../../hooks/usePagination.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {fetchKitchenTickets} from "../../client/kitchenTicketsApiClient.ts";
import {useQuery} from "@tanstack/react-query";

import styles from "./KitchenTicketList.module.css";

const {Paragraph} = Typography;

interface OrderListProps {
}

const KitchenTicketList: React.FC<OrderListProps> = ({}: OrderListProps) => {
  const navigate = useNavigate();
  const params = useParams()
  const {pageSize, currentPage, handlePageChange} = usePagination();

  const restaurantId = params.id as string;

  const {
    data: kitchenTicketsPage,
    isPending,
    error,
    refetch,
  } = useQuery<PageableRestApiResponse<BasicKitchenTicketRestDTO>, Error>({
    queryKey: ["kitchen-tickets", restaurantId],
    queryFn: fetchKitchenTickets.bind(null, restaurantId, currentPage - 1, pageSize),
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
            total: kitchenTicketsPage?.totalElements,
            onChange: handlePageChange,
            onShowSizeChange: handlePageChange,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
            responsive: true,
          }}
          dataSource={kitchenTicketsPage?.content || []}
          renderItem={ticket => (
              <List.Item>
                <Card
                    hoverable
                    className={styles.card}
                    styles={{body: {padding: '0px 24px 24px 24px'}}}
                    onClick={handleCardClick.bind(null, ticket.id)}
                    title={`Ticket ${ticket.id}`}
                >
                  <div className={styles.cardContent}>
                    <Paragraph>
                      <strong>Created:</strong> {`${new Date(ticket.createdAt).toLocaleString()}`}
                    </Paragraph>
                    <Paragraph>
                      <strong>Ticket status:</strong> {`${ticket.status}`}
                    </Paragraph>
                    <Paragraph>
                      <strong>Total:</strong> {`${ticket.totalPrice.toFixed(2)} €`}
                    </Paragraph>
                  </div>
                  <Button className={styles.btnPrimary} type="primary"
                          onClick={handleCardClick.bind(null, ticket.id)}>
                    View Details
                  </Button>
                </Card>
              </List.Item>
          )
          }
      />
  );
};

export default KitchenTicketList;
