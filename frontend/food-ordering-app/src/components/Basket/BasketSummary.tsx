import React from 'react';
import {Button, Card, Row, Typography, Divider} from 'antd';
import {useBasket} from "../../hooks/useBasketContext.tsx";

import styles from './BasketSummary.module.css';

const {Title, Text} = Typography;

interface BasketSummaryProps {
  onCheckout: () => void;
}

const BasketSummary: React.FC<BasketSummaryProps> = ({onCheckout}: BasketSummaryProps) => {
  const {totalItems, calculateTotalPrice} = useBasket();

  return (
      <Card className={styles.summaryCard}>
        <Title level={4}>Summary</Title>
        <Divider/>
        <Row className={styles.spaceBetween}>
          <Text>Total Items:</Text>
          <Text strong>{totalItems}</Text>
        </Row>
        <Row className={`${styles.spaceBetween} ${styles.marginTop}`}>
          <Text>Total Amount:</Text>
          <Text strong>€{calculateTotalPrice().toFixed(2)}</Text>
        </Row>
        <Divider/>
        <Button type="primary" block onClick={onCheckout}>
          Proceed to Checkout
        </Button>
      </Card>
  );
};

export default BasketSummary;