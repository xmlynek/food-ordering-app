import React from "react";
import {List} from "antd";

interface MenuListProps {
  menus: Array<MenuItem>;
}

const MenuList: React.FC<MenuListProps> = ({menus}: MenuListProps) => {

  return (
      <List
          itemLayout="horizontal"
          pagination={{position: 'bottom', align: 'start', pageSize: 5}}
          dataSource={menus}
          renderItem={item => (
              <List.Item>
                <p>
                  id: {item.id}
                </p>
                <br/>
                {/*<List.Item.Meta*/}
                {/*    avatar={<Avatar src={item.logo}/>}*/}
                {/*    title={*/}
                {/*      <p>{item.name}</p>} // Replace href with a link to the restaurant detail page*/}
                {/*    description={item.description}*/}
                {/*/>                */}
                <List.Item.Meta
                    title={item.name}
                    description={item.description}
                />
              </List.Item>
          )}
      />
  );
};

export default MenuList;