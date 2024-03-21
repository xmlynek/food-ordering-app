import {Affix, Layout, Menu} from "antd";
import {
  AppstoreOutlined,
  HomeOutlined, LogoutOutlined,
  ShoppingCartOutlined,
  UserOutlined
} from "@ant-design/icons";
import {Link, useLocation} from "react-router-dom";
import {performLogout} from "../../keycloak/keycloak.ts";

import styles from './Navigation.module.css';


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
      label: <Link to="/">Home</Link>, // Use Link component for navigation
    },
    {
      key: 'restaurants',
      icon: <AppstoreOutlined />,
      label: <Link to="/restaurants">Restaurants</Link>,
    },
    {
      key: 'basket',
      icon: <ShoppingCartOutlined />,
      label: <Link to="/basket">Basket</Link>,
    },
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: <Link to="/profile">Profile</Link>,
      className: styles.separateMenuItems,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: 'Logout',
      onClick: performLogout,
    },
  ];

  return (
      <Affix offsetTop={0}>
        <Layout.Header>
          <div className={styles.logo}>
            {/*<img src={logo} alt="logo"/>*/}
          </div>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={getSelectedKeys()} items={menuItems} />
        </Layout.Header>
      </Affix>
  );
};

export default Navbar;