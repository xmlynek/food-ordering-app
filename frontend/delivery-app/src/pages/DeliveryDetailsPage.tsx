import {Card, Layout, Space, Spin, Typography} from "antd";
import React from "react";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {Delivery} from "../model/delivery.ts";
import {fetchDeliveryById} from "../client/deliveryApiClient.ts";
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

  // const {mutateAsync: deleteRestaurantMutation} = useMutation({
  //   mutationFn: deleteRestaurant.bind(null, restaurantId), onSuccess: async () => {
  //     await message.success('Restaurant deleted successfully!');
  //     navigate('/restaurants');
  //   },
  //   onError: async (error) => {
  //     await message.error('Restaurant deletion failed: ' + error.message);
  //   }
  // });

  // const showDeleteConfirm = async () => {
  //   Modal.confirm({
  //     title: 'Do you really want to delete this restaurant?',
  //     content: 'This action cannot be undone',
  //     okText: 'Yes, delete it',
  //     okType: 'danger',
  //     cancelText: 'No',
  //     maskClosable: true,
  //     onOk: async () => {
  //       await deleteRestaurantMutation();
  //     },
  //   });
  // };

  const handleCompleteDelivery = async () => {

  }

  const handleAssignDelivery = async () => {

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
                                 onCompleteDelivery={handleCompleteDelivery}/>}
          </Card>
        </Space>
      </Content>
  );
};

export default DeliveryDetailsPage;