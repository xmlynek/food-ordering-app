import {Layout, Menu} from "antd";
import React from "react";
import {Link, useLocation} from "react-router-dom";
import {CheckCircleOutlined, UnorderedListOutlined} from "@ant-design/icons";


const {Content, Sider} = Layout;

interface RestaurantLayoutProps {
  children: React.ReactNode;
}

const RestaurantLayout: React.FC<RestaurantLayoutProps> = ({children}: RestaurantLayoutProps) => {

  const location = useLocation();

  const getSelectedKeys = () => {
    const path = location.pathname;
    if (path.includes('/menu/add')) return ['addMenu'];
    if (path.includes('/menu')) return ['actualMenu'];
    if (path.includes('/tickets')) return ['activeTickets'];
    return ['actualMenu'];
  };

  const menuItems = [
    {
      key: 'menu',
      icon: <CheckCircleOutlined/>,
      label: 'Menu',
      children: [
        {
          key: 'actualMenu',
          label: <Link to="menu" className="text-decoration-none">Actual Menu</Link>,
        },
        // {
        //   key: 'inactiveMenu',
        //   label: <Link to="/menu/inactive" className="text-decoration-none">Inactive Menu</Link>,
        // },
        {
          key: 'addMenu',
          label: <Link to="menu/add" className="text-decoration-none">Add Menu</Link>,
        },
      ],
    },
    {
      key: 'tickets',
      icon: <UnorderedListOutlined/>,
      label: 'Tickets',
      children: [
        {
          key: 'activeTickets',
          label: <Link to="tickets" className="text-decoration-none">Tickets</Link>,
        },
        // {
        //   key: 'finishedOrders',
        //   label: <Link to="/orders/finished" className="text-decoration-none">Finished Orders</Link>,
        // },
      ],
    },
  ];

  return (
      <Layout
          style={{padding: '24px 0', marginTop: '2rem'}}
      >
        <Sider>
          <Menu
              theme="dark"
              mode="inline"
              defaultSelectedKeys={getSelectedKeys()}
              defaultOpenKeys={['menu', 'tickets']}
              style={{height: '100%'}}
              items={menuItems}
          />
        </Sider>
        <Content style={{padding: '0 24px', minHeight: 280}}>
          {children}
        </Content>
      </Layout>
  );
};

export default RestaurantLayout;