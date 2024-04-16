import {Card, message, Space, Spin, Typography} from "antd";
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

const DeliveryDetailsPage: React.FC = () => {
  const params = useParams();
  const deliveryId = params.deliveryId as string

  const {
    data: deliveryDetails,
    error,
    isPending,
    refetch
  } = useQuery<Delivery, Error>({
    queryKey: ['delivery-details', deliveryId],
    queryFn: fetchDeliveryById.bind(null, deliveryId)
  });

  const {mutateAsync: assignDeliveryMutation, isPending: isAssignPending} = useMutation({
    mutationFn: assignDelivery.bind(null, deliveryId),
    onSuccess: async () => {
      await message.success('Delivery assigned successfully!');
    },
    onError: async (error) => {
      await message.error('Delivery assign failed: ' + error.message);
    }
  });

  const {mutateAsync: pickUpDeliveryMutation, isPending: isPickUpPending} = useMutation({
    mutationFn: pickUpDelivery.bind(null, deliveryId),
    onSuccess: async () => {
      await message.success('Delivery picked up successfully!');
    },
    onError: async (error) => {
      await message.error('Delivery pick up failed: ' + error.message);
    }
  });

  const {mutateAsync: completeDeliveryMutation, isPending: isCompletePending} = useMutation({
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
    await refetch();
  }

  const handlePickUpDelivery = async () => {
    await pickUpDeliveryMutation();
    await refetch();
  }

  const handleCompleteDelivery = async () => {
    await completeDeliveryMutation();
    await refetch();
  }

  return (
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Card style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px', marginTop: '15px'}}>
            <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
              <Title level={1} style={{marginTop: '10px'}}>Delivery details</Title>
            </Space>
            {isPending && <Spin size="large"/>}
            {error && <p>Error: {error.message}</p>}
            {!isPending && !error && deliveryDetails &&
                <DeliveryDetails deliveryDetails={deliveryDetails}
                                 isLoading={isPending || isAssignPending || isPickUpPending || isCompletePending}
                                 onAssignDelivery={handleAssignDelivery}
                                 onPickUpDelivery={handlePickUpDelivery}
                                 onCompleteDelivery={handleCompleteDelivery}/>}
          </Card>
       </Space>
  );
};

export default DeliveryDetailsPage;