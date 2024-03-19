import React from "react";
import {Col, Form, FormInstance, Input, Row, Space, Tooltip} from "antd";
import {
  EnvironmentOutlined,
  HomeOutlined,
  MailOutlined,
  QuestionCircleOutlined
} from "@ant-design/icons";
import {RestaurantFormValues} from "../../model/restaurant.ts";

interface CreateRestaurantFormProps {
  form: FormInstance<RestaurantFormValues>;
}

const CreateRestaurantForm: React.FC<CreateRestaurantFormProps> = ({form}: CreateRestaurantFormProps) => {
  return (
      <Form form={form} layout="vertical">
        <Row gutter={[16, 16]}>
          <Col span={24}>
            <Form.Item
                label={"Name"}
                name="name"
                rules={[{required: true, message: 'Please input the restaurant name!'}]}
            >
              <Input prefix={<HomeOutlined className="site-form-item-icon"/>}
                     placeholder="Restaurant Name"/>
            </Form.Item>
          </Col>
          <Col span={24}>
            <Form.Item
                label={<span>
                Description&nbsp;
                  <Tooltip
                      title="Restaurant's basic description and information. Max 255 characters.">
                  <QuestionCircleOutlined/>
                </Tooltip>
              </span>}
                name="description"
                rules={[{required: true, message: 'Please input the restaurant description!'}]}
            >
              <Input.TextArea count={{show: true, max: 255}} rows={4} maxLength={255}
                              placeholder="Describe the restaurant"/>
            </Form.Item>
          </Col>
        </Row>
        <Form.Item>
          <Space.Compact>
            <Row gutter={16}>
              <Col span={24}>
                <Form.Item
                    label={"Street"}
                    name={['address', 'street']}
                    rules={[{required: true, message: 'Please input the street!'}]}
                >
                  <Input prefix={<EnvironmentOutlined className="site-form-item-icon"/>}
                         placeholder="Street"/>
                </Form.Item>
              </Col>
              <Col xs={24} sm={12} md={12}>
                <Form.Item
                    label={"Postal Code"}
                    name={['address', 'postalCode']}
                    rules={[{required: true, message: 'Please input the postal code!'}]}
                >
                  <Input prefix={<MailOutlined className="site-form-item-icon"/>}
                         placeholder="Postal Code"/>
                </Form.Item>
              </Col>
              <Col xs={24} sm={12} md={12}>
                <Form.Item
                    label={"City"}
                    name={['address', 'city']}
                    rules={[{required: true, message: 'Please input the city!'}]}
                >
                  <Input prefix={<EnvironmentOutlined className="site-form-item-icon"/>}
                         placeholder="City"/>
                </Form.Item>
              </Col>
            </Row>
          </Space.Compact>
        </Form.Item>
      </Form>
  );
}

export default CreateRestaurantForm;