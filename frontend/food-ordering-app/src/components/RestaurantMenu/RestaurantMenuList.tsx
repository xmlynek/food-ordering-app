import React, {useEffect} from "react";
import {Button, Card, Col, Divider, Image, List, Row, Space, Typography} from "antd";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {DownOutlined, ShoppingCartOutlined, UpOutlined} from "@ant-design/icons";
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {fetchRestaurantMenus} from "../../client/catalogRestaurantMenuItemsApiClient.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {MenuItemRestDTO} from "../../model/restApiDto.ts";

import styles from './RestaurantMenuList.module.css';
import {usePagination} from "../../hooks/usePagination.ts";
import useSortableData from "../../hooks/useSortableData.ts";

const {Title, Paragraph} = Typography;

interface MenuListProps {
}

const RestaurantMenuList: React.FC<MenuListProps> = ({}: MenuListProps) => {
  const params = useParams();
  const {pageSize, currentPage, handlePageChange} = usePagination();
  const {addToBasket} = useBasket();

  const restaurantId = params.id as string;

  const {
    data: menuPage,
    isPending,
    error,
    refetch,
  } = useQuery<PageableRestApiResponse<MenuItemRestDTO>, Error>({
    queryKey: ["menus", restaurantId, currentPage, pageSize],
    queryFn: fetchRestaurantMenus.bind(null, restaurantId, currentPage - 1, pageSize),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize]);

  const {
    items: sortedMenuItems,
    requestSort,
    sortConfig,
    clearSort
  } = useSortableData<MenuItemRestDTO>(menuPage?.content || []);

  const handleAddToBasket = async (basketItem: BasketItem) => {
    addToBasket(basketItem);
  }

  if (error) return 'An error has occurred: ' + error.message

  return (
      <Card title="Available Menu Products" bordered={false} style={{boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px'}}>
        <Space style={{marginBottom: 16}} wrap>
          <Button onClick={() => requestSort("name")}>
            Sort by
            Name{sortConfig.key === "name" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={() => requestSort("price")}>
            Sort by
            Price {sortConfig.key === "price" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={clearSort}>Clear Sort</Button>
        </Space>
        <Divider style={{margin: "5px"}}/>
        <List
            className={styles.menuList}
            loading={isPending}
            itemLayout="horizontal"
            size="large"
            pagination={{
              position: 'bottom',
              align: 'end',
              pageSize: pageSize,
              current: currentPage,
              total: menuPage?.totalElements,
              onChange: handlePageChange,
              showSizeChanger: true,
              showQuickJumper: true,
              showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
              responsive: true,
            }}
            dataSource={sortedMenuItems}
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
      </Card>
  );
};

export default RestaurantMenuList;