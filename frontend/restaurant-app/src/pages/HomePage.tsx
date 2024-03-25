import {Menu, Space, Typography} from "antd";
import {MailOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";

const {Title} = Typography;

const HomePage = () => {

  const menuItems = [
    {
      key: 'restaurants',
      icon: <MailOutlined/>,
      label: <Link to="/restaurants">Restaurants</Link>,
    },
  ];

  return (
      <div style={{padding: '20px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>Restaurant Management System</Title>
        </ Space>
        <Menu mode="horizontal" items={menuItems}/>
      </div>
  );
};

export default HomePage;