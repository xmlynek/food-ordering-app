import {Layout, Menu} from "antd";
import {AppstoreOutlined, HomeOutlined, LogoutOutlined, UserOutlined} from "@ant-design/icons";
import {Link, useLocation} from "react-router-dom";
import {performLogout} from "../../keycloak/keycloak.ts";

import classes from './Navigation.module.css';


const Navbar = () => {
  const location = useLocation();

  const getSelectedKeys = () => {
    const path = location.pathname;
    if (path.includes('/restaurants')) return ['restaurants'];
    if (path.includes('/profile')) return ['profile'];
    return ['home'];
  };

  const menuItems = [
    {
      key: 'home',
      icon: <HomeOutlined />,
      label: <Link to="/">Home</Link>,
    },
    {
      key: 'restaurants',
      icon: <AppstoreOutlined />,
      label: <Link to="/restaurants">Restaurants</Link>,
    },
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: <Link to="/profile">Profile</Link>,
      className: classes.separateMenuItems,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: 'Logout',
      onClick: performLogout,
    },
  ];

  return (
      <Layout.Header>
        <div className={classes.logo}>
          {/*<img src={logo} alt="logo"/>*/}
        </div>
        <Menu theme="dark" mode="horizontal" items={menuItems} selectedKeys={getSelectedKeys()} />
      </Layout.Header>
  );
};

export default Navbar;