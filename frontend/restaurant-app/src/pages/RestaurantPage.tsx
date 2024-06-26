import {Button, Card, Col, message, Modal, Row, Space, Spin, Typography} from "antd";
import React from "react";
import {Outlet, useNavigate, useParams} from "react-router-dom";
import RestaurantDetails from "../components/Restaurant/RestaurantDetails.tsx";
import RestaurantDetailsLayout from "../components/Restaurant/layout/RestaurantDetailsLayout.tsx";
import {useMutation, useQuery} from "@tanstack/react-query";
import {Restaurant} from "../model/restaurant.ts";
import {
  deleteRestaurant,
  fetchRestaurantById
} from "../client/restaurantApiClient.ts";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import ModifyRestaurantModal from "../components/Restaurant/ModifyRestaurantModal.tsx";

const {Title} = Typography;

const RestaurantPage: React.FC = () => {
  const params = useParams();
  const navigate = useNavigate();
  const [isModalVisible, setIsModalVisible] = React.useState<boolean>(false);

  const restaurantId = params.id as string

  const {
    data: restaurant,
    error,
    isPending,
  } = useQuery<Restaurant, Error>({
    queryKey: ['restaurantById', restaurantId],
    queryFn: fetchRestaurantById.bind(null, restaurantId)
  });

  const {mutateAsync: deleteRestaurantMutation} = useMutation({
    mutationFn: deleteRestaurant.bind(null, restaurantId), onSuccess: async () => {
      await message.success('Restaurant deleted successfully!');
      navigate('/restaurants');
    },
    onError: async (error) => {
      await message.error('Restaurant deletion failed: ' + error.message);
    }
  });

  const showDeleteConfirm = async () => {
    Modal.confirm({
      title: 'Do you really want to delete this restaurant?',
      content: 'This action cannot be undone',
      okText: 'Yes, delete it',
      okType: 'danger',
      cancelText: 'No',
      maskClosable: true,
      onOk: async () => {
        await deleteRestaurantMutation();
      },
    });
  };

  const handleModifyButton = () => {
    setIsModalVisible(true);
  }
  const handleModalCancel = () => {
    setIsModalVisible(false);
  }

  if (error) return 'An error has occurred: ' + error.message;
  if (isPending) return <Spin size="large"/>;


  return (
      <Card>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center', marginBottom: '10px'}}>
          <Title level={1} style={{marginTop: '10px'}}>{restaurant.name}</Title>
        </Space>

        <Card style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}} title={
          <Row justify="space-between" align="middle">
            <Col>Restaurant Details</Col>
            <Col>
              <Space>
                <Button type="primary" icon={<EditOutlined/>}
                        onClick={handleModifyButton}>Modify</Button>
                <Button danger icon={<DeleteOutlined/>} onClick={showDeleteConfirm}>Delete</Button>
              </Space>
            </Col>
          </Row>
        }>
          <RestaurantDetails restaurant={restaurant}/>
        </Card>

        <RestaurantDetailsLayout>
          <Outlet/>
        </RestaurantDetailsLayout>

        <ModifyRestaurantModal restaurant={restaurant} isVisible={isModalVisible}
                               onHideModal={handleModalCancel}/>
      </Card>
  );
};

export default RestaurantPage;