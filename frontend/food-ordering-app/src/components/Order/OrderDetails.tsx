import {Badge, Card, Col, Image, List, Row, Typography} from "antd";
import React from "react";
import {OrderDetailsDto} from "../../model/order.ts";
import {DeliveryStatus, KitchenTicketStatus, OrderStatus} from "../../model/enum.ts";

const {Title} = Typography;


interface OrderDetailsProps {
  orderDetails: OrderDetailsDto;
}

const OrderDetails: React.FC<OrderDetailsProps> = ({orderDetails}: OrderDetailsProps) => {

  // TODO: update this function to return the correct color based on the order status
  const getOrderStatusColor = (status: OrderStatus) => {
    switch (status) {
      case OrderStatus.PENDING:
        return 'processing';
      case OrderStatus.PAID:
        return 'processing';
      case OrderStatus.APPROVED:
        return 'processing';
      case OrderStatus.COMPLETED:
        return 'success';
      case OrderStatus.CANCELLING:
        return 'warning';
      case OrderStatus.CANCELLED:
        return 'error';
      default:
        return 'default';
    }
  };

  const getKitchenStatusColor = (status: KitchenTicketStatus) => {
    switch (status) {
      case KitchenTicketStatus.PREPARING:
        return 'processing';
      case KitchenTicketStatus.READY_FOR_DELIVERY:
        return 'processing';
      case KitchenTicketStatus.FINISHED:
        return 'success';
      case KitchenTicketStatus.REJECTED:
        return 'error';
      default:
        return 'default';
    }
  };

  const getDeliveryStatusColor = (status: DeliveryStatus) => {
    switch (status) {
      case DeliveryStatus.WAITING_FOR_KITCHEN:
        return 'default';
      case DeliveryStatus.AT_DELIVERY:
        return 'processing';
      case DeliveryStatus.DELIVERED:
        return 'success';
      default:
        return 'default';
    }
  };

  return (
      <Card
          title={<Title ellipsis={true} style={{marginTop: '0px'}}
                        level={4}>Order {orderDetails.id}</Title>}
          style={{width: '100%'}}
          headStyle={{background: '#f0f2f5'}}
      >
        <Row gutter={[16, 16]}>
          <Col xs={24} sm={12}>
            <Typography.Text strong>Created
              At:</Typography.Text> {new Date(orderDetails.createdAt).toLocaleString()}
          </Col>
          <Col xs={24} sm={12}>
            <Typography.Text strong>Updated
              At:</Typography.Text> {new Date(orderDetails.lastModifiedAt || orderDetails.createdAt).toLocaleString()}
          </Col>
          <Col xs={24} sm={12}>
            <Typography.Text strong>Total
              Price:</Typography.Text> ${orderDetails.totalPrice.toFixed(2)}
          </Col>
          <Col xs={24} sm={12}>
            <Typography.Text strong>Restaurant ID:</Typography.Text> {orderDetails.restaurantId}
          </Col>
          <Col xs={24} sm={12}>
            <Typography.Text strong>Order Status: </Typography.Text>
            <Badge status={getOrderStatusColor(orderDetails.orderStatus)}
                   text={orderDetails.orderStatus}/>
          </Col>
          {orderDetails.kitchenTicketStatus && (<Col xs={24} sm={12}>
            <Typography.Text strong>Kitchen Status: </Typography.Text>
            <Badge status={getKitchenStatusColor(orderDetails.kitchenTicketStatus)}
                   text={orderDetails.kitchenTicketStatus}/>
          </Col>)}
          {orderDetails.deliveryStatus && (<Col xs={24} sm={12}>
            <Typography.Text strong>Delivery Status: </Typography.Text>
            <Badge status={getDeliveryStatusColor(orderDetails.deliveryStatus)}
                   text={orderDetails.deliveryStatus}/>
          </Col>)}


          {(orderDetails.orderStatus === OrderStatus.CANCELLED || orderDetails.orderStatus === OrderStatus.CANCELLING) && (
              <Col xs={24}>
                <Typography.Text strong>Error
                  Message:</Typography.Text> {orderDetails.failureMessage}
              </Col>)}
        </Row>

        <List
            header={<Title level={4} style={{marginBottom: '20px'}}>Order Items</Title>}
            itemLayout="vertical"
            size="large"
            dataSource={orderDetails?.items}
            renderItem={item => (
                <List.Item style={{
                  borderRadius: '8px',
                  boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
                  marginBottom: '16px',
                  padding: '16px',
                  transition: 'box-shadow 0.3s',
                }}>
                  <Row gutter={[16, 16]} align="middle" wrap>
                    <Col xs={24} sm={8} md={6} lg={4} xl={4}>
                      <Image
                          src={item.imageUrl}
                          style={{
                            width: '100px',
                            height: '100px',
                            objectFit: 'cover',
                            borderRadius: '8px',
                          }}
                      />
                    </Col>
                    <Col xs={24} sm={10} md={14} lg={16} xl={16}>
                      <Typography.Title level={5}
                                        style={{marginBottom: '4px'}}>{item.name}</Typography.Title>
                      <div>
                        <Typography.Text strong>ID: </Typography.Text> {item.productId}
                      </div>
                      <div>
                        <Typography.Text strong>Description: </Typography.Text> {item.description}
                      </div>
                    </Col>
                    <Col xs={24} sm={6} md={4} lg={4} xl={4}>
                      <Typography.Text
                          style={{display: 'block'}}>Quantity: {item.quantity}</Typography.Text>
                      <Typography.Text style={{display: 'block'}}>Price:
                        €{item.price.toFixed(2)}</Typography.Text>
                      <Typography.Text strong style={{display: 'block'}}>Total:
                        €{(item.price * item.quantity).toFixed(2)}</Typography.Text>
                    </Col>
                  </Row>
                </List.Item>
            )}
        />
      </Card>
  );
};

export default OrderDetails;
