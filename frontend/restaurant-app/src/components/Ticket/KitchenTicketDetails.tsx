import React from "react";
import {Button, Card, Image, List, Typography} from "antd";
import {KitchenTicketDetailsRestDTO} from "../../model/kitchenTicket.ts";
import {CheckCircleOutlined} from "@ant-design/icons";

const {Title} = Typography;

interface OrderListProps {
  ticketDetails: KitchenTicketDetailsRestDTO;
  isPending: boolean;
  onCompleteTicket: () => void;
}

const KitchenTicketDetails: React.FC<OrderListProps> = ({
                                                          ticketDetails,
                                                          isPending,
                                                          onCompleteTicket
                                                        }: OrderListProps) => {


  return (
      <>
        <div style={{marginBottom: '20px'}}>
          <Title level={5} style={{marginBottom: '5px'}}>Ticket ID: {ticketDetails.id}</Title>
          <Title level={5} style={{marginBottom: '5px'}}>Status: {ticketDetails.status}</Title>
          <Title level={5} style={{marginBottom: '5px'}}>Total Price:
            €{ticketDetails.totalPrice.toFixed(2)}</Title>
          {ticketDetails.deliveryStatus && <Title level={5} style={{marginBottom: '5px'}}>Delivery
            Status: {ticketDetails.deliveryStatus}</Title>}

        </div>

        <List
            header={<Title level={5}>Products</Title>}
            itemLayout="horizontal"
            loading={isPending}
            dataSource={ticketDetails.ticketItems || []}
            renderItem={item => (
                <List.Item>
                  <Card
                      hoverable
                      cover={<Image alt={item.name} src={item.imageUrl}/>}
                  >
                    <Card.Meta
                        title={item.name}
                        description={`Price: €${item.price.toFixed(2)} - Quantity: ${item.quantity}`}
                    />
                  </Card>
                </List.Item>
            )}
        />
        {ticketDetails?.status === "PREPARING" && (
            <div style={{display: 'flex', justifyContent: 'center', marginTop: '20px'}}>
              <Button type="primary" size={"large"} icon={<CheckCircleOutlined/>}
                      onClick={onCompleteTicket}
                      style={{backgroundColor: '#52c41a', borderColor: '#52c41a'}}>
                Mark as ready
              </Button>
            </div>
        )}
      </>
  );
};

export default KitchenTicketDetails;
