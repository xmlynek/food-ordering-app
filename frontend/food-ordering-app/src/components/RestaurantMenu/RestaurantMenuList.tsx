import React from "react";
import {Button, Col, Image, List, Row, Space, Typography} from "antd";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {ShoppingCartOutlined} from "@ant-design/icons";
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {fetchRestaurantMenus} from "../../client/catalogRestaurantMenuItemsApiClient.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {MenuItemRestDTO} from "../../model/restApiDto.ts";

import styles from './RestaurantMenuList.module.css';

const {Title, Paragraph} = Typography;

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
              <List.Item>
                <Row gutter={[16, 16]} align={"top"} justify={"center"} style={{width: "100%"}}>
                  <Col xs={24} sm={24} md={24} lg={10} xl={12}>
                    <Image src={item.imageUrl} className={styles.imageResponsive}/>
                  </Col>
                  <Col xs={24} sm={24} md={24} lg={12} xl={12}>
                    <Space direction={"vertical"} className={styles.spaceStyles}>
                      <List.Item.Meta
                          title={<Title level={4}
                                        className={styles.titleStyles}>{item.name}</Title>}
                          description={<Paragraph
                              className={styles.paragraphStyles}>{item.description}</Paragraph>}
                      />
                      <Paragraph strong>Price: €{item.price.toFixed(2)}</Paragraph>
                      <Button
                          type="primary"
                          block
                          icon={<ShoppingCartOutlined/>}
                          onClick={() => handleAddToBasket({
                            id: item.id,
                            name: item.name,
                            restaurantId: params.id as string,
                            price: item.price,
                            description: item.description,
                            imageUrl: item.imageUrl,
                            quantity: 1, // TODO: Adjust accordingly
                          })}
                      >
                        Add to Basket
                      </Button>
                    </Space>
                  </Col>
                </Row>
              </List.Item>
          )}
      />
  );
};

export default RestaurantMenuList;