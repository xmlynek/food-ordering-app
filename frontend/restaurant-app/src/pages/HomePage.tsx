import {Menu, Typography} from "antd";
import {MailOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";

const { Title } = Typography;

const HomePage = () => {

  const menuItems = [
    {
      key: 'restaurants',
      icon: <MailOutlined />,
      label: <Link to="/restaurants">Restaurants</Link>,
    },
  ];

  return (
      <div style={{ padding: '20px' }}>
        <Title level={1}>Welcome to Our Restaurant Management System</Title>
        <Menu mode="horizontal" items={menuItems} />
      </div>
  );
};

export default HomePage;