import React from "react";
import {Avatar, Button, List} from "antd";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {MenuItem} from "../../model/restaurant.ts";
import styles from './MenuList.module.css';
import {useNavigate} from "react-router-dom";

interface MenuListProps {
  menus: Array<MenuItem>;
  isPending: boolean;
  deleteHandler: (menuId: string) => void;
}

const MenuList: React.FC<MenuListProps> = ({menus, deleteHandler, isPending}: MenuListProps) => {
  const navigate = useNavigate();

  const handleModify = (id: string) => {
    console.log(`Modifying item with id: ${id}`);
    navigate(`${id}/edit`)
  };


  return (
      <List
          className={styles.menuList}
          loading={isPending}
          itemLayout="vertical"
          size="large"
          pagination={{
            position: 'bottom',
            pageSize: 5,
          }}
          dataSource={menus}
          renderItem={item => (
              <List.Item
                  key={item.id}
                  actions={[
                    <Button key="list-modify" icon={<EditOutlined/>} type="primary"
                            onClick={() => handleModify(item.id)}>Modify</Button>,
                    <Button key="list-delete" icon={<DeleteOutlined/>} type="primary" danger
                            onClick={() => deleteHandler(item.id)}>Delete</Button>
                  ]}
              >
                <List.Item.Meta
                    avatar={<Avatar src={item.imageUrl} size={128}/>}
                    title={<p onClick={handleModify.bind(null, item.id)}>{item.name}</p>}
                    description={item.description}
                />
                <div className={styles.menuPrice}>Price: €{item.price.toFixed(2)}</div>
              </List.Item>
          )}
      />
  );
};

export default MenuList;