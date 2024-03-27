import React, {useEffect} from "react";
import {Avatar, Button, List, Tag} from "antd";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {useNavigate, useParams} from "react-router-dom";
import {usePagination} from "../../hooks/usePagination.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {fetchRestaurantMenuItems} from "../../client/restaurantMenuItemsApiClient.ts";
import {MenuItem} from "../../model/menuItem.ts";

import styles from './MenuList.module.css';
import {useQuery} from "@tanstack/react-query";


interface MenuListProps {
  deleteHandler: (menuId: string) => void;
}

const MenuList: React.FC<MenuListProps> = ({deleteHandler}: MenuListProps) => {
  const navigate = useNavigate();
  const params = useParams();
  const {pageSize, currentPage, handlePageChange} = usePagination();

  const restaurantId = params.id as string;

  const {
    data: menuItemsPage,
    error,
    isPending,
    refetch,
  } = useQuery<PageableRestApiResponse<MenuItem>, Error>({
    queryKey: ['menus', restaurantId],
    queryFn: fetchRestaurantMenuItems.bind(null, restaurantId, currentPage - 1, pageSize),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize]);

  const handleModify = (id: string) => {
    navigate(`${id}/edit`)
  };

  if (error) return 'An error has occurred: ' + error.message

  return (
      <List
          className={styles.menuList}
          loading={isPending}
          itemLayout="vertical"
          size="large"
          pagination={{
            position: 'bottom',
            align: 'start',
            pageSize: pageSize,
            current: currentPage,
            total: menuItemsPage?.totalElements,
            onChange: handlePageChange,
            onShowSizeChange: handlePageChange,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
            responsive: true,
          }}
          dataSource={menuItemsPage?.content || []}
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
                    title={<div><p onClick={handleModify.bind(null, item.id)}>{item.name}</p>
                      <span>{item.isAvailable ? (<Tag color="green">Available</Tag>) : (
                          <Tag color="red">Not available</Tag>)}
                          </span></div>}
                    description={item.description}
                />
                <div className={styles.menuPrice}>Price: €{item.price.toFixed(2)}</div>
              </List.Item>
          )}
      />
  );
};

export default MenuList;