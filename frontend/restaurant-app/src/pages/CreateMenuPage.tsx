import {Form, message, Modal} from "antd";
import React from "react";
import {useNavigate, useParams} from "react-router-dom";
import MenuItemFormSkeleton from "../components/Menu/MenuItemFormSkeleton.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import UploadFormItem from "../components/UI/UploadFormItem.tsx";
import {createRestaurantMenuItem} from "../client/restaurantMenuItemsApiClient.ts";
import {MenuItemFormValues} from "../model/menuItem.ts";


const CreateMenuPage: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<MenuItemFormValues>();
  const params = useParams();
  const navigate = useNavigate();

  const restaurantId = params.id as string;


  const {mutateAsync, isPending} = useMutation({
    mutationFn: createRestaurantMenuItem.bind(null, restaurantId), onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['menus']});
      await message.success('Menu created successfully!');
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
      message.error('Menu creation failed!');
    }
  };
  const handleCancel = () => {
    // form.resetFields(); // not necessary when destroyOnClose is true
    navigate(`../`);
  };


  return (
      <Modal
          title="Create new menu"
          open={true}
          onOk={handleOk}
          confirmLoading={isPending}
          onCancel={handleCancel}
          destroyOnClose={true}
          maskClosable={false}
      >
        <MenuItemFormSkeleton form={form}>
          <UploadFormItem form={form}/>
        </MenuItemFormSkeleton>
      </Modal>
  );
};

export default CreateMenuPage;