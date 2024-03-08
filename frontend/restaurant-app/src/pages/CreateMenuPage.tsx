import {Form, message, Modal} from "antd";
import React from "react";
import {useNavigate, useParams} from "react-router-dom";
import MenuItemFormSkeleton from "../components/Menu/MenuItemFormSkeleton.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import axios from "axios";
import {MenuItemFormValues} from "../model/restaurant.ts";
import UploadFormItem from "../components/UI/UploadFormItem.tsx";


const CreateMenuPage: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<MenuItemFormValues>();
  const params = useParams();
  const navigate = useNavigate();

  const createMenu = async (menuData: MenuItemFormValues) => {
    const formData = new FormData();
    formData.append('menuItemRequest', new Blob([JSON.stringify({
      name: menuData.name,
      description: menuData.description,
      price: menuData.price
    })], {type: 'application/json'}));
    formData.append('image', menuData.image);

    const response = await axios.post(`http://localhost:8085/api/restaurants/${params.id}/menu`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return response.data;
  };

  const {mutateAsync, isPending} = useMutation({
    mutationFn: createMenu, onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['menus']});
      message.success('Menu created successfully!');
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