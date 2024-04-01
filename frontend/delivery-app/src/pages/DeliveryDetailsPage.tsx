import {Card, Layout, message, Space, Spin, Typography} from "antd";
import React from "react";
import {useParams} from "react-router-dom";
import {useMutation, useQuery} from "@tanstack/react-query";
import {Delivery} from "../model/delivery.ts";
import {
  assignDelivery,
  completeDelivery,
  fetchDeliveryById,
  pickUpDelivery
} from "../client/deliveryApiClient.ts";
import DeliveryDetails from "../components/Delivery/DeliveryDetails.tsx";

const {Title} = Typography;

const {Content} = Layout;

const DeliveryDetailsPage: React.FC = () => {
  const params = useParams();
  const deliveryId = params.deliveryId as string

  const {
    data: deliveryDetails,
    error,
    isPending,
  } = useQuery<Delivery, Error>({
    queryKey: ['delivery-details', deliveryId],
    queryFn: fetchDeliveryById.bind(null, deliveryId)
  });

  const {mutateAsync: assignDeliveryMutation} = useMutation({
    mutationFn: assignDelivery.bind(null, deliveryId),
    onSuccess: async () => {
      await message.success('Delivery assigned successfully!');
    },
    onError: async (error) => {
      await message.error('Delivery assign failed: ' + error.message);
    }
  });

  const {mutateAsync: pickUpDeliveryMutation} = useMutation({
    mutationFn: pickUpDelivery.bind(null, deliveryId),
    onSuccess: async () => {
      await message.success('Delivery picked up successfully!');
    },
    onError: async (error) => {
      await message.error('Delivery pick up failed: ' + error.message);
    }
  });

  const {mutateAsync: completeDeliveryMutation} = useMutation({
    mutationFn: completeDelivery.bind(null, deliveryId),
    onSuccess: async () => {
      await message.success('Delivery completed successfully!');
    },
    onError: async (error) => {
      await message.error('Delivery completion failed: ' + error.message);
    }
  });

  const handleAssignDelivery = async () => {
    await assignDeliveryMutation();
  }

  const handlePickUpDelivery = async () => {
    await pickUpDeliveryMutation();
  }

  const handleCompleteDelivery = async () => {
    await completeDeliveryMutation();
  }

  return (
      <Content>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>Delivery details</Title>
        </Space>

        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Card>
            {isPending && <Spin size="large"/>}
            {error && <p>Error: {error.message}</p>}
            {!isPending && !error && deliveryDetails &&
                <DeliveryDetails deliveryDetails={deliveryDetails}
                                 onAssignDelivery={handleAssignDelivery}
                                 onPickUpDelivery={handlePickUpDelivery}
                                 onCompleteDelivery={handleCompleteDelivery}/>}
          </Card>
        </Space>
      </Content>
  );
};

export default DeliveryDetailsPage;