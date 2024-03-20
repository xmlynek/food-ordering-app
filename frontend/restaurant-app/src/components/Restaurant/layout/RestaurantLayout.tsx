import {Layout, Menu} from "antd";
import React from "react";
import {Link} from "react-router-dom";
import {CheckCircleOutlined, UnorderedListOutlined} from "@ant-design/icons";


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
              mode="inline"
              defaultSelectedKeys={['actualMenu']}
              defaultOpenKeys={['menu', 'orders']}
              style={{height: '100%'}}
              // items={[{key: "orders", title: "Orders", label: "Orders"}, {
              //   key: "2",
              //   title: "Menu",
              //   label: "Menu"
              // }]}
          >
            <Menu.SubMenu key="menu" icon={<CheckCircleOutlined />} title="Menu">
              <Menu.Item key="actualMenu">
                <Link to="menu" className="text-decoration-none">Actual Menu</Link>
              </Menu.Item>
              {/*<Menu.Item key="inactiveMenu">*/}
              {/*  <Link to="/menu/inactive" className="text-decoration-none">Inactive Menu</Link>*/}
              {/*</Menu.Item>*/}
              <Menu.Item key="addMenu">
                <Link to="menu/add" className="text-decoration-none">Add Menu</Link>
              </Menu.Item>
            </Menu.SubMenu>
            <Menu.SubMenu key="orders" icon={<UnorderedListOutlined />} title="Orders">
              <Menu.Item key="activeOrders">
                <Link to="orders" className="text-decoration-none">Orders</Link>
              </Menu.Item>
              {/*<Menu.Item key="finishedOrders">*/}
              {/*  <Link to="/orders/finished" className="text-decoration-none">Finished Orders</Link>*/}
              {/*</Menu.Item>*/}
            </Menu.SubMenu>
          </Menu>
        </Sider>
        <Content style={{padding: '0 24px', minHeight: 280}}>
          {children}
        </Content>
      </Layout>
  );
};

export default RestaurantLayout;