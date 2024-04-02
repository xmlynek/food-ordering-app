import React from "react";
import {Button, Card, Space, Spin, Typography} from "antd";
import {Delivery, DeliveryStatus, KitchenTicketStatus} from "../../model/delivery.ts";
import {getAddressAsString} from "../../model/address.ts";
import {CheckCircleOutlined, ScheduleOutlined, TruckOutlined} from "@ant-design/icons";

import styles from "./Delivery.module.css";
import keycloak from "../../keycloak/keycloak.ts";

const {Paragraph} = Typography;

interface OrderListProps {
  deliveryDetails: Delivery;
  onAssignDelivery: () => void;
  onPickUpDelivery: () => void;
  onCompleteDelivery: () => void;
  isLoading: boolean;
}

const DeliveryDetails: React.FC<OrderListProps> = ({
                                                     deliveryDetails,
                                                     onAssignDelivery,
                                                     onPickUpDelivery,
                                                     onCompleteDelivery,
                                                     isLoading,
                                                   }: OrderListProps) => {

  const isAssignable = (): boolean => {
    return deliveryDetails.deliveryStatus === DeliveryStatus.WAITING_FOR_KITCHEN &&
        deliveryDetails.courierId === null &&
        (deliveryDetails.kitchenTicketStatus === KitchenTicketStatus.PREPARING ||
            deliveryDetails.kitchenTicketStatus === KitchenTicketStatus.READY_FOR_DELIVERY);
  }

  const isPickUpAble = (): boolean => {
    return deliveryDetails.deliveryStatus === DeliveryStatus.WAITING_FOR_KITCHEN &&
        deliveryDetails.kitchenTicketStatus === KitchenTicketStatus.READY_FOR_DELIVERY &&
        deliveryDetails.courierId === keycloak.subject;
  }

  const isCompletable = (): boolean => {
    return deliveryDetails.deliveryStatus === DeliveryStatus.AT_DELIVERY &&
        deliveryDetails.courierId === keycloak.subject;
  }

  return (
      <Card
          styles={{body: {padding: '0px 24px 24px 24px'}}}
          title={`Delivery ${deliveryDetails.id}`}
      >
        {isLoading && <Spin fullscreen={true} size={"large"} tip={<p>Loading...</p>}/>}
        <div className={styles.cardContent}>
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
            <strong>Kitchen status:</strong> {`${deliveryDetails.kitchenTicketStatus}`}
          </Paragraph>
          <Paragraph>
            <strong>Delivery status:</strong> {`${deliveryDetails.deliveryStatus}`}
          </Paragraph>
          <Paragraph>
            <strong>Last
              update:</strong> {`${new Date(deliveryDetails.lastModifiedAt).toLocaleString()}`}
          </Paragraph>
          <Paragraph>
            <strong>Restaurant ID:</strong> {deliveryDetails.restaurantId}
          </Paragraph>
          <Paragraph>
            <strong>Customer ID:</strong> {deliveryDetails.customerId}
          </Paragraph>
          {deliveryDetails.courierId && (<Paragraph>
            <strong>Courier ID:</strong> {deliveryDetails.courierId}
          </Paragraph>)}
        </div>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>

          {isAssignable() && (
              <Button type="dashed" style={{backgroundColor: 'orange'}} icon={<ScheduleOutlined/>}
                      size={"large"}
                      onClick={onAssignDelivery}>Assign delivery</Button>)}
          {!isAssignable() && !isCompletable() && deliveryDetails.deliveryStatus !== DeliveryStatus.DELIVERED && (
              <Button type="dashed" disabled={!isPickUpAble()}
                      style={{backgroundColor: 'yellowgreen'}}
                      icon={<TruckOutlined/>}
                      size={"large"}
                      onClick={onPickUpDelivery}>Pick up
                delivery</Button>)}

          {isCompletable() && (
              <Button type="primary" className={styles.completeButton}
                      icon={<CheckCircleOutlined/>} size={"large"}
                      onClick={onCompleteDelivery}>Mark Completed</Button>)}

        </Space>
      </Card>
  );
};

export default DeliveryDetails;
