import {Menu, Space, Typography} from "antd";
import {MailOutlined} from "@ant-design/icons";
import React from "react";

const {Title} = Typography;

const HomePage: React.FC = () => {
  return (
      <div style={{padding: '20px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>Food ordering application</Title>
        </Space>
        <Menu mode="horizontal">
          <Menu.Item key="restaurants" icon={<MailOutlined/>}>
            <a href="/restaurants">Restaurants</a>
          </Menu.Item>
          {/*<Menu.Item key="management" icon={<MailOutlined/>}>*/}
          {/*  <a href="/management">Restaurant Management</a>*/}
          {/*</Menu.Item>*/}
        </Menu>
      </div>
  );
};

export default HomePage;