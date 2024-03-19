import {Form, message, Modal} from "antd";
import React from "react";
import {useNavigate} from "react-router-dom";
import CreateRestaurantForm from "../components/Restaurant/CreateRestaurantForm.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {createRestaurant} from "../client/restaurantApiClient.ts";



const CreateRestaurantPage: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm();
  const navigate = useNavigate();


  const {mutateAsync, isPending, error} = useMutation({
    mutationFn: createRestaurant, onSuccess: async (data) => {
      await queryClient.invalidateQueries({queryKey: ['restaurants']});
      await message.success(`Restaurant ${data.name} created successfully`);
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
      await message.error('Restaurant creation failed!');
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