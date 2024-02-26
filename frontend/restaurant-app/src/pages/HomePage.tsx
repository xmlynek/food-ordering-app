import {Menu, Typography} from "antd";
import {MailOutlined} from "@ant-design/icons";

const { Title } = Typography;

const HomePage = () => {
  return (
      <div style={{ padding: '20px' }}>
        <Title level={1}>Welcome to Our Restaurant Management System</Title>
        <Menu mode="horizontal">
          <Menu.Item key="orders" icon={<MailOutlined />}>
            <a href="/orders">Orders</a>
          </Menu.Item>
          <Menu.Item key="management" icon={<MailOutlined />}>
            <a href="/management">Restaurant Management</a>
          </Menu.Item>
        </Menu>
      </div>
  );
};

export default HomePage;