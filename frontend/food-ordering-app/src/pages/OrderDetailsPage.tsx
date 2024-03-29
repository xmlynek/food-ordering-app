import {Row, Spin, Typography} from "antd";
import React from "react";
import {useQuery} from "@tanstack/react-query";
import {OrderDetailsDto} from "../model/order.ts";
import {fetchOrderDetails} from "../client/ordersApiClient.ts";
import {useParams} from "react-router-dom";

const {Title} = Typography;
import OrderDetails from "../components/Order/OrderDetails.tsx";


const OrderDetailsPage: React.FC = () => {
  const params = useParams();

  const orderId = params.orderId as string;

  const {
    data: orderDetails,
    error,
    isPending,

  } = useQuery<OrderDetailsDto, Error>({
    queryKey: ['order-details', orderId],
    queryFn: fetchOrderDetails.bind(null, orderId),
  });


  return (
      <div>
        <Row justify="center" style={{marginBottom: '12px'}}>
          <Title style={{marginTop: '0px'}} level={1}>Order details</Title>
        </Row>
        {isPending && <Spin size="large"/>}
        {error && <div>An error has occurred: {error.message}</div>}
        {!isPending && !error && <OrderDetails orderDetails={orderDetails} />}
      </div>
  );
};

export default OrderDetailsPage;
