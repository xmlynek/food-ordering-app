import React from "react";
import {Avatar, Button, List} from "antd";
import {MenuItem} from "../../model/restaurant.ts";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {ShoppingCartOutlined} from "@ant-design/icons";
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {fetchRestaurantMenus} from "../../client/restaurantMenuItemsApiClient.ts";

interface MenuListProps {
}

const RestaurantMenuList: React.FC<MenuListProps> = ({}: MenuListProps) => {
  const params = useParams();
  const { addToBasket } = useBasket();
  const restaurantId = params.id as string;

  const {data: menus, isPending, error} = useQuery<MenuItem[], Error>({
    queryKey: ["menus", restaurantId],
    queryFn: fetchRestaurantMenus.bind(null, restaurantId),
  });


  if (error) return 'An error has occurred: ' + error.message

  const handleAddToBasket = async (basketItem: BasketItem)=> {
    addToBasket(basketItem);
  }

  return (
      <List
          loading={isPending}
          itemLayout="horizontal"
          pagination={{ position: 'bottom', pageSize: 10 }}
          dataSource={menus?.content || []}
          renderItem={item => (
              <List.Item
                  actions={[
                    <Button
                        type="primary"
                        icon={<ShoppingCartOutlined />}
                        onClick={() => handleAddToBasket({
                          id: item.id,
                          name: item.name,
                          restaurantId: params.id as string,
                          price: item.price,
                          quantity: 1,
                        })}
                    >
                      Add to Basket
                    </Button>
                  ]}
              >
                <List.Item.Meta
                    avatar={<Avatar src={item.imageUrl || 'fallbackImageUrl'} />} // Replace 'fallbackImageUrl' with an actual URL or remove the property
                    title={item.name}
                    description={item.description}
                />
              </List.Item>
          )}
      />
  );
};

export default RestaurantMenuList;