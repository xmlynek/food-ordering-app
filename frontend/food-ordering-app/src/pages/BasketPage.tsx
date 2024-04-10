import {Typography, Row, Col, Layout, Result, Button} from 'antd';
import React from "react";
import BasketItemList from "../components/Basket/BasketItemList.tsx";
import BasketSummary from "../components/Basket/BasketSummary.tsx";
import {useBasket} from "../hooks/useBasketContext.tsx";
import {useNavigate} from "react-router-dom";

const {Title} = Typography;
const {Content} = Layout;

const BasketPage: React.FC = () => {
  const {basket, removeFromBasket, updateQuantity} = useBasket();
  const navigate = useNavigate();

  const handleQuantityChange = async (item: BasketItem, number: number) => {
    if (number <= 0) {
      removeFromBasket(item.id);
    } else {
      updateQuantity(item.id, number);
    }
  };

  const handleCheckout = () => {
    navigate('/checkout');
  };

  return (
      <Content>
        <Row justify="center" style={{marginBottom: '12px'}}>
          <Title level={1}>Your Basket</Title>
        </Row>


        {basket.length > 0 ? (
            <Row gutter={[16, 16]} align="middle" justify={"center"}>
              <Col xs={24} md={24} lg={24} style={{maxWidth: '920px'}}>
                <BasketItemList basket={basket} onQuantityChange={handleQuantityChange}
                                onRemoveFromBasket={removeFromBasket}/>
              </Col>
              <Col xs={24} md={24} lg={24} style={{maxWidth: '920px'}}>
                <BasketSummary onCheckout={handleCheckout}/>
              </Col>
            </Row>
        ) : (
            <Result
                status="404"
                title="Your Basket is Empty"
                subTitle="Looks like you haven't added anything to your basket yet."
                extra={<Button type="primary" onClick={() => navigate('/restaurants')}>Start
                  Shopping</Button>}
            />
        )}
      </Content>
  );
};

export default BasketPage;