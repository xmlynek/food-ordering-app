import {Affix, Badge, Layout, Menu, Tooltip} from "antd";
import {
  AppstoreOutlined, HistoryOutlined,
  HomeOutlined, LogoutOutlined,
  ShoppingCartOutlined,
  UserOutlined
} from "@ant-design/icons";
import {Link, useLocation} from "react-router-dom";
import {performLogout} from "../../keycloak/keycloak.ts";

import styles from './Navigation.module.css';
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {animated, useSpring} from "react-spring";


const Navbar = () => {

  const {calculateTotalPrice, totalItems} = useBasket();
  const location = useLocation();

  const AnimatedBadge = animated(Badge);

  const springProps = useSpring({
    to: {transform: 'scale(1.1)'},
    from: {transform: 'scale(1)'},
    reset: true,
    reverse: false,
  });

  const getSelectedKeys = () => {
    const path = location.pathname;
    if (path.includes('/restaurants')) return ['restaurants'];
    if (path.includes('/profile')) return ['profile'];
    if (path.includes('/order-history')) return ['order-history'];
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
      key: 'restaurants',
      icon: <AppstoreOutlined/>,
      label: <Link to="/restaurants">Restaurants</Link>,
    },
    {
      key: 'basket',
      icon: <ShoppingCartOutlined style={{fontSize: '1rem'}}/>,
      label:
          <Link to="/basket">Basket
            <Tooltip
                title={`Total: $${calculateTotalPrice().toFixed(2)}`}>
              <AnimatedBadge
                  offset={[7, -5]}
                  count={totalItems}
                  overflowCount={99}
                  style={{backgroundColor: '#52c41a', ...springProps}}/>
            </Tooltip>
          </Link>,
      className: styles.separateMenuItems,
    },
    {
      key: 'profile',
      icon: <UserOutlined/>,
      label: <Link to="/profile">Profile</Link>,
    },
    {
      key: 'order-history',
      icon: <HistoryOutlined/>,
      label: <Link to="/order-history">Order History</Link>,
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
            {/*<img src={logo} alt="logo"/>*/}
          </div>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={getSelectedKeys()}
                items={menuItems}/>
        </Layout.Header>
      </Affix>
  );
};

export default Navbar;