import {Layout, Typography} from "antd";
import React, {useState} from "react";
import {Order} from "../model/order.ts";
import MenuList from "../components/Menu/MenuList.tsx";

const {Title} = Typography;
const {Content} = Layout;

const MenusPage: React.FC = () => {

  const [menus, setMenus] = useState<Array<MenuItem>>([{
    id: "1",
    name: "Pizza",
    description: "Delicious pizza with a variety of toppings.",
    price: 10.55
  }]);

  return (
      <Content>
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of menus</Title>
        {/*</ Space>*/}
        <MenuList menus={menus}/>

      </Content>
  );
};

export default MenusPage;