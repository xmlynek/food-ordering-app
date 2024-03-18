import React from "react";
import {Avatar, Button, Image, List} from "antd";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {ShoppingCartOutlined} from "@ant-design/icons";
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {fetchRestaurantMenus} from "../../client/catalogRestaurantMenuItemsApiClient.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {MenuItemRestDTO} from "../../model/restApiDto.ts";

import styles from './RestaurantMenuList.module.css';

interface MenuListProps {
}

const RestaurantMenuList: React.FC<MenuListProps> = ({}: MenuListProps) => {
  const params = useParams();
  const {addToBasket} = useBasket();
  const restaurantId = params.id as string;

  const {
    data: menuPage,
    isPending,
    error
  } = useQuery<PageableRestApiResponse<MenuItemRestDTO>, Error>({
    queryKey: ["menus", restaurantId],
    queryFn: fetchRestaurantMenus.bind(null, restaurantId),
  });


  if (error) return 'An error has occurred: ' + error.message

  const handleAddToBasket = async (basketItem: BasketItem) => {
    addToBasket(basketItem);
  }

  return (
      <List
          className={styles.menuList}
          loading={isPending}
          itemLayout="horizontal"
          size="large"
          pagination={{
            position: 'bottom',
            align: 'start',
            pageSize: menuPage?.size || 10,
            total: menuPage?.totalElements || 0,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`
          }}
          dataSource={menuPage?.content}
          renderItem={item => (
              <List.Item
                  actions={[
                    <Button
                        type="primary"
                        icon={<ShoppingCartOutlined/>}
                        onClick={() => handleAddToBasket({
                          id: item.id,
                          name: item.name,
                          restaurantId: params.id as string,
                          price: item.price,
                          description: item.description,
                          imageUrl: item.imageUrl,
                          quantity: 1, // TODO: Add a quantity input field to the UI
                        })}
                    >
                      Add to Basket
                    </Button>
                  ]}
              >
                <List.Item.Meta
                    avatar={<Image src={item.imageUrl} height={96} width={96} className={styles.imageIcon}/>}
                    title={<p>{item.name}</p>}
                    description={item.description}
                />
                <div className={styles.menuPrice}>Price: €{item.price.toFixed(2)}</div>
              </List.Item>
          )}
      />
  );
};

export default RestaurantMenuList;