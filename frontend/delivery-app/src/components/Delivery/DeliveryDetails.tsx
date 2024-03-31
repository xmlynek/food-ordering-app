import React from "react";
import {Button, Card, Space, Typography} from "antd";
import {Delivery, DeliveryStatus} from "../../model/delivery.ts";
import {getAddressAsString} from "../../model/address.ts";
import {CheckCircleOutlined, ScheduleOutlined} from "@ant-design/icons";

import styles from "./Delivery.module.css";

const {Paragraph} = Typography;

interface OrderListProps {
  deliveryDetails: Delivery;
  onAssignDelivery: () => void;
  onCompleteDelivery: () => void;
}

const DeliveryDetails: React.FC<OrderListProps> = ({
                                                     deliveryDetails,
                                                     onAssignDelivery,
                                                     onCompleteDelivery,
                                                   }: OrderListProps) => {


  return (
      <Card
          styles={{body: {padding: '0px 24px 24px 24px'}}}
          title={`Delivery ${deliveryDetails.id}`}
      >
        <div className={styles.cardContent}>
          <Paragraph>
            <strong>Last
              update:</strong> {`${new Date(deliveryDetails.lastModifiedAt).toLocaleString()}`}
          </Paragraph>
          <Paragraph>
            <strong>Delivery status:</strong> {`${deliveryDetails.deliveryStatus}`}
          </Paragraph>
          <Paragraph>
            <strong>Restaurant name:</strong> {deliveryDetails.restaurantName}
          </Paragraph>
          <Paragraph>
            <strong>Restaurant
              address:</strong> {getAddressAsString(deliveryDetails.restaurantAddress)}
          </Paragraph>
          <Paragraph>
            <strong>Delivery
              address:</strong> {getAddressAsString(deliveryDetails.deliveryAddress)}
          </Paragraph>
          <Paragraph>
            <strong>Restaurant ID:</strong> {deliveryDetails.restaurantId}
          </Paragraph>
          <Paragraph>
            <strong>Customer ID:</strong> {deliveryDetails.customerId}
          </Paragraph>
        </div>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>

          {(deliveryDetails.deliveryStatus === DeliveryStatus.READY_FOR_DELIVERY || deliveryDetails.deliveryStatus === DeliveryStatus.WAITING_FOR_KITCHEN) && (
              <Button type="dashed" style={{backgroundColor: 'orange'}} icon={<ScheduleOutlined/>}
                      size={"large"}
                      onClick={onAssignDelivery}>Assign delivery</Button>)}

          {deliveryDetails.deliveryStatus === DeliveryStatus.AT_DELIVERY && (
              <Button type="primary" className={styles.completeButton}
                      icon={<CheckCircleOutlined/>} size={"large"}
                      onClick={onCompleteDelivery}>Mark Completed</Button>)}

        </Space>
      </Card>
  );
};

export default DeliveryDetails;
