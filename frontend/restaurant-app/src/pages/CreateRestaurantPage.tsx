import {Form, message, Modal} from "antd";
import React from "react";
import {useNavigate} from "react-router-dom";
import CreateRestaurantForm from "../components/Restaurant/CreateRestaurantForm.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";



const CreateRestaurantPage: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm();
  const navigate = useNavigate();

  const createRestaurant = async (restaurantData) => {
    const response = await fetch(`http://localhost:8085/api/restaurants`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(restaurantData),
    });

    if (!response.ok) {
      throw new Error('Failed to create restaurant');
    }

    return response.json();
  };

  const {mutateAsync, isPending, error} = useMutation({
    mutationFn: createRestaurant, onSuccess: async (data) => {
      await queryClient.invalidateQueries({queryKey: ['restaurants']});
      message.success(`Restaurant ${data.name} created successfully`);
    }
  });

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      console.log('Form Values:', values);

      await mutateAsync(values);

      form.resetFields();
      navigate(`../`);
    } catch (errorInfo) {
      console.log('Failed:', errorInfo);
      message.error('Restaurant creation failed!');
    }
  };
  const handleCancel = () => {
    // form.resetFields();
    navigate(`../`);

  };

  return (
      <Modal
          title="Create new restaurant"
          open={true}
          onOk={handleOk}
          confirmLoading={isPending}
          onCancel={handleCancel}
          destroyOnClose={true}
          maskClosable={false}
      >
        <CreateRestaurantForm form={form}/>
      </Modal>
  );
};

export default CreateRestaurantPage;