import React from 'react';
import {Button, Card, Row, Col, Avatar, InputNumber, Typography, Space} from 'antd';
import {
  DeleteOutlined,
  MinusOutlined, PlusOutlined
} from '@ant-design/icons';

import styles from './BasketItemList.module.css';

const {Text} = Typography;

interface BasketItemListProps {
  basket: BasketItem[];
  onQuantityChange: (item: BasketItem, number: number) => void;
  onRemoveFromBasket: (itemId: string) => void;
}

const BasketItemList: React.FC<BasketItemListProps> = ({
                                                         basket,
                                                         onQuantityChange,
                                                         onRemoveFromBasket
                                                       }) => {

  return (
      <Card>
        {basket.map((item) => (
            <Card key={item.id} bordered={true}>
              <Row justify="space-between" align="middle" gutter={[16, 16]}>
                <Col xs={24} sm={7} md={4} lg={3} xl={3}>
                  <Avatar src={item.imageUrl} shape="square" size={92} />
                </Col>
                <Col xs={24} sm={17} md={9} lg={9} xl={9}>
                  <Text strong>{item.name}</Text>
                  <br />
                  <Text type="secondary">€{item.price.toFixed(2)} each</Text>
                </Col>
                <Col xs={24} sm={16} md={7} lg={5} xl={5}>
                  <Space>
                    <Button shape="circle" icon={<MinusOutlined />} onClick={() => onQuantityChange(item, item.quantity - 1)} />
                    <InputNumber min={1} value={item.quantity} className={styles.quantityInput} onChange={(value) => onQuantityChange(item, Number(value))} />
                    <Button shape="circle" icon={<PlusOutlined />} onClick={() => onQuantityChange(item, item.quantity + 1)} />
                  </Space>
                </Col>
                <Col xs={24} sm={8} md={3} lg={3} xl={3}>
                  <Text strong >€{(item.price * item.quantity).toFixed(2)}</Text>
                </Col>
                <Col xs={24} sm={24} md={24} lg={4} xl={4}>
                  <Button danger block icon={<DeleteOutlined />} onClick={() => onRemoveFromBasket(item.id)}>
                    Remove
                  </Button>
                </Col>
              </Row>
            </Card>
        ))}
      </Card>
  );
};

export default BasketItemList;