import React from "react";
import {
  Form,
  FormInstance, Input,
  InputNumber
} from "antd";
import {MenuItemFormValues} from "../../model/restaurant.ts";

interface CreateMenuFormProps {
  form: FormInstance;
  initialValues?: MenuItemFormValues;
  children?: React.ReactNode;
}


const MenuItemFormSkeleton: React.FC<CreateMenuFormProps> = ({
                                                       form,
                                                       initialValues, children
                                                     }: CreateMenuFormProps) => {

  return (
      <Form initialValues={initialValues ? initialValues : {}} form={form} layout="vertical"
            name="addMenuForm">
        <Form.Item
            name="name"
            label="Name"
            rules={[{required: true, message: 'Please input the menu name!'}]}
        >
          <Input/>
        </Form.Item>
        <Form.Item
            name="description"
            label="Description"
            rules={[{required: true, message: 'Please input the menu description!'}]}
        >
          <Input.TextArea rows={4}/>
        </Form.Item>
        <Form.Item
            name="price"
            label="Price"
            rules={[{required: true, message: 'Please input the menu price!'}]}
        >
          <InputNumber min={0}
                       formatter={value => `€ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}/>
        </Form.Item>
        {children}
      </Form>
  );
}

export default MenuItemFormSkeleton;