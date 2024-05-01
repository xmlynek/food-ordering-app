import {Affix, Layout, Menu} from "antd";
import {
  HistoryOutlined,
  HomeOutlined, LogoutOutlined,
  UserOutlined, TruckOutlined,
} from "@ant-design/icons";
import {Link, useLocation} from "react-router-dom";
import {performLogout} from "../../keycloak/keycloak.ts";

import styles from './Navigation.module.css';

import logo from '../../assets/logo.jpg';


const Navbar = () => {
  const location = useLocation();

  const getSelectedKeys = () => {
    const path = location.pathname;
    if (path.includes('/delivery-history')) return ['delivery-history'];
    if (path.includes('/delivery')) return ['delivery'];
    if (path.includes('/profile')) return ['profile'];
    if (path.includes('/basket')) return ['basket'];
    return ['home'];
  };

  const menuItems = [
    {
      key: 'home',
      icon: <HomeOutlined/>,
      label: <Link to="/">Home</Link>,
    },
    {
      key: 'delivery',
      icon: <TruckOutlined/>,
      label: <Link to="/delivery">Delivery Orders</Link>,
    },
    {
      key: 'profile',
      icon: <UserOutlined/>,
      label: <Link to="/profile">Profile</Link>,
      className: styles.separateMenuItems,
    },
    {
      key: 'delivery-history',
      icon: <HistoryOutlined/>,
      label: <Link to="/delivery-history">Delivery History</Link>,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined/>,
      label: 'Logout',
      onClick: performLogout,
    },
  ];

  return (
      <Affix offsetTop={0}>
        <Layout.Header>
          <div className={styles.logo}>
            <img src={logo} alt="logo" className={styles.logoImage}/>
          </div>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={getSelectedKeys()}
                items={menuItems}/>
        </Layout.Header>
      </Affix>
  );
};

export default Navbar;