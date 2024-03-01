import React from "react";
import {Button, List} from "antd";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {MenuItem} from "../../model/restaurant.ts";

interface MenuListProps {
  menus: Array<MenuItem>;
  isPending: boolean;
  deleteHandler: (menuId: string) => void;
}

const MenuList: React.FC<MenuListProps> = ({menus, deleteHandler, isPending}: MenuListProps) => {

  const handleModify = (id: string) => {
    console.log(`Modifying item with id: ${id}`);
    // Show a modal with form for modification
    // or navigate to a different page/component with the item's details pre-filled for editing
  };


  return (
      <List
          loading={isPending}
          itemLayout="horizontal"
          pagination={{position: 'bottom', align: 'start', pageSize: 25}}
          dataSource={menus}
          renderItem={item => (
              <List.Item
                  actions={[
                    <Button key="list-modify" icon={<EditOutlined/>} type="primary"
                            onClick={() => handleModify(item.id)}>Modify</Button>,
                    <Button key="list-delete" icon={<DeleteOutlined/>} type="primary" danger
                            onClick={() => deleteHandler(item.id)}>Delete</Button>
                  ]}
              >
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