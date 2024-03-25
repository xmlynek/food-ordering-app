import React from 'react';
import {Button, Card, Row, Col, Avatar, InputNumber, Typography} from 'antd';
import {MinusCircleOutlined, PlusCircleOutlined, DeleteOutlined} from '@ant-design/icons';

import styles from './BasketItemList.module.css';

const {Title, Text} = Typography;

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
      basket.map((item, index) => (
          <Card
              key={index}
              className={styles.cardMargin}
              actions={[
                <Button icon={<DeleteOutlined/>} onClick={() => onRemoveFromBasket(item.id)} danger>
                  Remove
                </Button>,
              ]}
          >
            <Row gutter={16} align="middle">
              <Col xs={6} sm={4} md={3} lg={4}>
                <Avatar size={64} src={item.imageUrl || 'placeholderImage.png'} alt={item.name}/>
              </Col>
              <Col xs={18} sm={20} md={21} lg={20}>
                <Title level={5}>{item.name}</Title>
                <Text>€{item.price.toFixed(2)} each</Text>
                <div className={styles.quantityControl}>
                  <Button icon={<MinusCircleOutlined/>}
                          onClick={() => onQuantityChange(item, item.quantity - 1)}/>
                  <InputNumber
                      min={1}
                      value={item.quantity}
                      onChange={(value) => onQuantityChange(item, value !== null ? value : 1)}
                      className={styles.marginX}
                  />
                  <Button icon={<PlusCircleOutlined/>}
                          onClick={() => onQuantityChange(item, item.quantity + 1)}/>
                </div>
              </Col>
            </Row>
          </Card>
      ))
  );
};

export default BasketItemList;