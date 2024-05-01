import {Form, message, Modal} from "antd";
import React from "react";
import {useNavigate} from "react-router-dom";
import RestaurantForm from "../components/Restaurant/RestaurantForm.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {createRestaurant} from "../client/restaurantApiClient.ts";
import {RestaurantFormValues} from "../model/restaurant.ts";


const CreateRestaurantPage: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<RestaurantFormValues>();
  const navigate = useNavigate();


  const {mutateAsync, isPending} = useMutation({
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
        <RestaurantForm form={form}/>
      </Modal>
  );
};

export default CreateRestaurantPage;