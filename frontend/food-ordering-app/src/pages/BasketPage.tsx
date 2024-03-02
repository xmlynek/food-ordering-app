import {Button, Card, Typography, Divider, Row, Col, Avatar, InputNumber} from 'antd';
import { MinusCircleOutlined, PlusCircleOutlined, DeleteOutlined } from '@ant-design/icons';
import {useBasket} from "../hooks/useBasketContext.tsx";
import React from "react";


const { Title, Text } = Typography;

const BasketPage: React.FC = () => {
  const { basket, removeFromBasket, updateQuantity } = useBasket(); // Make sure to implement these functions
  const calculateTotal = () => {
    return basket.reduce((total, item) => total + item.price * item.quantity, 0);
  };

  function handleQuantityChange(item: BasketItem, number: number) {
    if (number <= 0) {
      removeFromBasket(item.id);
    } else {
      updateQuantity(item.id, number);
    }
  }

  return (
      <div style={{ padding: '24px' }}>
        <Row gutter={[16, 16]}>
          <Col xs={24} lg={16}>
            {basket.map((item, index) => (
                <Card
                    key={index}
                    style={{ marginBottom: '16px', boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}
                    actions={[
                      <Button icon={<DeleteOutlined />} onClick={() => removeFromBasket(item.id)} danger>
                        Remove
                      </Button>,
                    ]}
                >
                  <Row gutter={16} align="middle">
                    <Col xs={6} sm={4} md={3} lg={4}>
                      <Avatar size={64} src={item.image || 'placeholderImage.png'} alt={item.name} />
                    </Col>
                    <Col xs={18} sm={20} md={21} lg={20}>
                      <Title level={5}>{item.name}</Title>
                      <Text>${item.price.toFixed(2)} each</Text>
                      <div style={{ marginTop: '16px', display: 'flex', alignItems: 'center' }}>
                        <Button icon={<MinusCircleOutlined />} onClick={() => handleQuantityChange(item, item.quantity - 1)} />
                        <InputNumber min={1} value={item.quantity} onChange={(value) => handleQuantityChange(item, value)} style={{ margin: '0 8px' }} />
                        <Button icon={<PlusCircleOutlined />} onClick={() => handleQuantityChange(item, item.quantity + 1)} />
                      </div>
                    </Col>
                  </Row>
                </Card>
            ))}
          </Col>
          <Col xs={24} lg={8}>
            <Card style={{ boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}>
              <Title level={4}>Summary</Title>
              <Divider />
              <Row justify="space-between">
                <Text>Total Items:</Text>
                <Text>{basket.length}</Text>
              </Row>
              <Row justify="space-between" style={{ marginTop: '16px' }}>
                <Text>Total Amount:</Text>
                <Text>${calculateTotal().toFixed(2)}</Text>
              </Row>
              <Divider />
              <Button type="primary" block>
                Proceed to Checkout
              </Button>
            </Card>
          </Col>
        </Row>
      </div>
  );
};

export default BasketPage;