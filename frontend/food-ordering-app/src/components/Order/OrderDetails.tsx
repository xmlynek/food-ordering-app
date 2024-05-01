import {Badge, Card, Col, Image, List, Row, Space, Typography} from "antd";
import React from "react";
import {OrderDetailsDto} from "../../model/order.ts";
import {DeliveryStatus, KitchenTicketStatus, OrderStatus} from "../../model/enum.ts";

const {Title} = Typography;


interface OrderDetailsProps {
  orderDetails: OrderDetailsDto;
}

const OrderDetails: React.FC<OrderDetailsProps> = ({orderDetails}: OrderDetailsProps) => {

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
          style={{width: '100%', boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}}
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
              Price:</Typography.Text> €{orderDetails.totalPrice.toFixed(2)}
          </Col>
          <Col xs={24} sm={12}>
            <Typography.Text strong>Restaurant Name:</Typography.Text> {orderDetails.restaurantName}
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
                  <Row gutter={[16, 16]} align="middle" justify="space-evenly" wrap>
                    <Col xs={24} sm={7} md={5} lg={3} xl={3}>
                      <Image
                          src={item.imageUrl}
                          style={{
                            width: '100%',
                            height: 'auto',
                            objectFit: 'cover',
                            borderRadius: '8px',
                          }}
                      />
                    </Col>
                    <Col xs={24} sm={17} md={15} lg={17} xl={17}>
                      <Space direction="vertical">
                        <Typography.Title level={5}
                                          style={{margin: 0}}>{item.name}</Typography.Title>
                        <div>
                          <Typography.Text strong>ID: </Typography.Text> {item.productId}
                        </div>
                        <div style={{textAlign: 'justify'}}>
                          <Typography.Text strong>Description: </Typography.Text> {item.description}
                        </div>
                      </Space>
                    </Col>
                    <Col xs={24} sm={24} md={4} lg={3} xl={3}>
                      <Space direction="vertical">
                        <Typography.Text
                            style={{display: 'block'}}>Quantity: {item.quantity}</Typography.Text>
                        <Typography.Text style={{display: 'block'}}>Price:
                          €{item.price.toFixed(2)}</Typography.Text>
                        <Typography.Text strong style={{display: 'block'}}>Total:
                          €{(item.price * item.quantity).toFixed(2)}</Typography.Text>
                      </Space>
                    </Col>
                  </Row>
                </List.Item>
            )}
        />
      </Card>
  );
};

export default OrderDetails;
