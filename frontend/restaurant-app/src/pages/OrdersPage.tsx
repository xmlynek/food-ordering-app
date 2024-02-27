import React, {useState} from "react";
import {Layout, Typography} from "antd";
import {Order} from "../model/order.ts";
import OrderList from "../components/Order/OrderList.tsx";


const {Title} = Typography;
const {Content} = Layout;

const OrdersPage: React.FC = () => {

  const [orders] = useState<Array<Order>>([{id: "sdffdsfsd"}, {id: "ssdfsdf"}]);

  return (
      <Content>
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of orders</Title>
        {/*</ Space>*/}
        <OrderList orders={orders}/>

      </Content>
  );
};

export default OrdersPage;