import {Layout, Menu} from "antd";
import React from "react";
import {Link} from "react-router-dom";


const {Content, Sider} = Layout;

interface RestaurantLayoutProps {
  children: React.ReactNode;
}

const RestaurantLayout: React.FC<RestaurantLayoutProps> = ({children}: RestaurantLayoutProps) => {
  return (
      <Layout
          style={{padding: '24px 0'}}
      >
        <Sider>
          <Menu
              theme="dark"
              mode="vertical"
              // defaultSelectedKeys={['orders']}
              // defaultOpenKeys={['orders']}
              style={{height: '100%'}}
              // items={[{key: "orders", title: "Orders", label: "Orders"}, {
              //   key: "2",
              //   title: "Menu",
              //   label: "Menu"
              // }]}
          >
            <Menu.Item key={'orders'} defaultChecked={true}>
              <Link to={'orders'} className="text-decoration-none">
                <span className="nav-text h6">Orders</span>
              </Link>
            </Menu.Item>
            <Menu.Item key={'menu'}>
              <Link to={'menu'} className="text-decoration-none">
                <span className="nav-text h6">menu</span>
              </Link>
            </Menu.Item>
          </Menu>
        </Sider>
        <Content style={{padding: '0 24px', minHeight: 280}}>
          {children}
        </Content>
      </Layout>
  );
};

export default RestaurantLayout;