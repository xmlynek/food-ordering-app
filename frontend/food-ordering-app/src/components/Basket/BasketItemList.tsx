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
      <>
        {basket.map((item) => (
            <Card key={item.id} bordered={false} className={styles.card}>
              <Row justify="space-between" align="middle" gutter={16}>
                <Col>
                  <Avatar src={item.imageUrl} shape="square" size={64}/>
                </Col>
                <Col>
                  <Text strong>{item.name}</Text>
                  <br/>
                  <Text type="secondary">€{item.price.toFixed(2)} each</Text>
                </Col>
                <Col>
                  <Space>
                    <Button shape="circle" icon={<MinusOutlined/>}
                            onClick={() => onQuantityChange(item, item.quantity - 1)}/>
                    <InputNumber min={1} value={item.quantity} className={styles.quantityInput}
                                 onChange={(value) => onQuantityChange(item, Number(value))}/>
                    <Button shape="circle" icon={<PlusOutlined/>}
                            onClick={() => onQuantityChange(item, item.quantity + 1)}/>
                  </Space>
                </Col>
                <Col>
                  <Button danger icon={<DeleteOutlined/>}
                          onClick={() => onRemoveFromBasket(item.id)}>
                    Remove
                  </Button>
                </Col>
                <Col>
                  <Text strong>€{(item.price * item.quantity).toFixed(2)}</Text>
                </Col>
              </Row>
            </Card>
        ))}
      </>
  );
};

export default BasketItemList;