import {Affix, Layout, Menu} from "antd";
import {AppstoreOutlined, HomeOutlined, ShoppingCartOutlined} from "@ant-design/icons";
import styles from './Navigation.module.css';
import {Link} from "react-router-dom";

const Navbar = () => {
  return (
      <Affix offsetTop={0}>
        <Layout.Header>
          <div className={styles.logo}>
            {/*<img src={logo} alt="logo"/>*/}
          </div>
          {/*TODO: set defaultSelectedkey to the current page*/}
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['home']}>
            <Menu.Item key="home" icon={<HomeOutlined/>}>
              <Link to="/">Home</Link>
            </Menu.Item>
            <Menu.Item key="restaurants" icon={<AppstoreOutlined/>}>
              <Link to="/restaurants">Restaurants</Link>
            </Menu.Item>
            <Menu.Item key="basket" icon={<ShoppingCartOutlined/>}>
              <Link to="/basket">Basket</Link>
            </Menu.Item>
          </Menu>
        </Layout.Header>
      </Affix>
  );
};

export default Navbar;