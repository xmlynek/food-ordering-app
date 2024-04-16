import React, {useEffect} from "react";
import {Button, Card, Divider, List, Space, Typography} from "antd";
import {BasicKitchenTicketRestDTO} from "../../model/kitchenTicket.ts";
import {useParams, useSearchParams} from "react-router-dom";

import {usePagination} from "../../hooks/usePagination.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {fetchKitchenTickets} from "../../client/kitchenTicketsApiClient.ts";
import {useQuery} from "@tanstack/react-query";

import styles from "./KitchenTicketList.module.css";
import {DownOutlined, UpOutlined} from "@ant-design/icons";
import useSortableData from "../../hooks/useSortableData.ts";
import KitchenTicketDetailsWrapper from "./KitchenTicketDetailsWrapper.tsx";

const {Paragraph} = Typography;

interface OrderListProps {
}

const KitchenTicketList: React.FC<OrderListProps> = ({}: OrderListProps) => {
  const params = useParams()
  const [searchParams] = useSearchParams();
  const {pageSize, currentPage, handlePageChange} = usePagination();
  const [ticketDetails, setTicketDetails] = React.useState<{
    visible: boolean;
    ticketId: string
  }>({visible: false, ticketId: ""});

  const restaurantId = params.id as string;
  const ticketStatusParam = searchParams.get("ticketStatus") || "";

  const {
    data: kitchenTicketsPage,
    isPending,
    error,
    refetch,
  } = useQuery<PageableRestApiResponse<BasicKitchenTicketRestDTO>, Error>({
    queryKey: ["kitchen-tickets", restaurantId],
    queryFn: fetchKitchenTickets.bind(null, restaurantId, currentPage - 1, pageSize, ticketStatusParam),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize, searchParams]);


  const handleCardClick = (ticketId: string) => {
    setTicketDetails({visible: true, ticketId: ticketId});
  }

  const handleCloseTicketDetails = () => {
    setTicketDetails({visible: false, ticketId: ""});
  }

  const {
    items: sortedTickets,
    requestSort,
    sortConfig,
    clearSort
  } = useSortableData<BasicKitchenTicketRestDTO>(kitchenTicketsPage?.content || []);

  if (error) {
    return <p>Error: {error.message}</p>;
  }

  return (
      <>
        <KitchenTicketDetailsWrapper isVisible={ticketDetails.visible}
                                     ticketId={ticketDetails.ticketId}
                                     restaurantId={restaurantId}
                                     onClose={handleCloseTicketDetails}/>

        <Space style={{marginBottom: 16}}>
          <Button onClick={() => requestSort("createdAt")}>
            Sort by
            Created{sortConfig.key === "createdAt" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={() => requestSort("totalPrice")}>
            Sort by Total {sortConfig.key === "totalPrice" && (sortConfig.direction === "ascend" ?
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
              total: kitchenTicketsPage?.totalElements,
              onChange: handlePageChange,
              onShowSizeChange: handlePageChange,
              showSizeChanger: true,
              showQuickJumper: true,
              showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
              responsive: true,
            }}
            dataSource={sortedTickets || []}
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
      </>
  );
};

export default KitchenTicketList;
