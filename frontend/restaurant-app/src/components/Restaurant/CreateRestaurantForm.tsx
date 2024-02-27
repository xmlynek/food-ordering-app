import React from "react";
import {Form, FormInstance, Input} from "antd";

interface CreateRestaurantFormProps {
  form: FormInstance;
}

const CreateRestaurantForm: React.FC<CreateRestaurantFormProps> = ({form}: CreateRestaurantFormProps) => {
  return (
      <Form form={form}>
        <Form.Item
            label="Name"
            name="name"
            rules={[{required: true, message: 'Please input the restaurant name!'}]}
        >
          <Input/>
        </Form.Item>
      </Form>
  );
}

export default CreateRestaurantForm;