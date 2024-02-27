import {Form, message, Modal} from "antd";
import React from "react";
import {useNavigate, useParams} from "react-router-dom";
import CreateMenuForm from "../components/Menu/CreateMenuForm.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";


const CreateMenuPage: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm();
  const params = useParams();
  const navigate = useNavigate();

  const createMenu = async (menuData) => {
    const response = await fetch(`http://localhost:8085/api/restaurants/${params.id}/menu`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(menuData),
    });

    if (!response.ok) {
      throw new Error('Failed to create menu');
    }

    return response.json();
  };

  const {mutateAsync, isPending, error} = useMutation({
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
    // form.resetFields();
    navigate(`../`);

  };


  return (
      <>
        <Modal
            title="Create new menu"
            open={true}
            onOk={handleOk}
            confirmLoading={isPending}
            onCancel={handleCancel}
            destroyOnClose={true}
            maskClosable={false}
        >
          <CreateMenuForm form={form}/>
        </Modal>
      </>
  );
};

export default CreateMenuPage