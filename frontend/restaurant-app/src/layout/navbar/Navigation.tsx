import {Layout, Menu} from "antd";
import {AppstoreOutlined, HomeOutlined, SettingOutlined} from "@ant-design/icons";
import styles from './Navigation.module.css';
import {Link} from "react-router-dom";

const Navbar = () => {
  return (
      <Layout.Header>
        <div className={styles.logo}>
          {/*<img src={logo} alt="logo"/>*/}
        </div>
         {/*TODO: set defaultSelectedkey to the current page*/}
        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
          <Menu.Item key="1" icon={<HomeOutlined/>}>
            <Link to="/">Home</Link>
          </Menu.Item>
          <Menu.Item key="restaurants" icon={<AppstoreOutlined/>}>
            <Link to="/restaurants">Restaurants</Link>
          </Menu.Item>
          <Menu.Item key="3" icon={<SettingOutlined/>}>
            <Link to="/management">Management</Link>
          </Menu.Item>
        </Menu>
      </Layout.Header>
  );
};

export default Navbar;