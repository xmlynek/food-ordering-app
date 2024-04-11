import React, {useEffect, useState} from "react";
import {Button, Col, Divider, Image, List, Row, Space, Typography} from "antd";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {DownOutlined, ShoppingCartOutlined, UpOutlined} from "@ant-design/icons";
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {fetchRestaurantMenus} from "../../client/catalogRestaurantMenuItemsApiClient.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {MenuItemRestDTO} from "../../model/restApiDto.ts";

import styles from './RestaurantMenuList.module.css';
import {usePagination} from "../../hooks/usePagination.ts";

const {Title, Paragraph} = Typography;

interface MenuListProps {
}

const RestaurantMenuList: React.FC<MenuListProps> = ({}: MenuListProps) => {
  const params = useParams();
  const {pageSize, currentPage, handlePageChange} = usePagination();
  const {addToBasket} = useBasket();
  const [sortDirection, setSortDirection] = useState<"ascend" | "descend" | "">("");
  const [sortType, setSortType] = useState<"name" | "price" | "">("");

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


  const handleAddToBasket = async (basketItem: BasketItem) => {
    addToBasket(basketItem);
  }

  const sortedData = () => {
    return [...menuPage?.content || []].sort((a, b) => {
      if (sortType === "name") {
        return sortDirection === "ascend" ? a.name.localeCompare(b.name) : b.name.localeCompare(a.name);
      } else if (sortType === "price") {
        return sortDirection === "ascend" ? a.price - b.price : b.price - a.price;
      }
      return 0;
    });
  };

  const handleSort = (type: "name" | "price") => {
    const isAscending = sortType !== type || sortDirection === "descend";
    setSortType(type);
    setSortDirection(isAscending ? "ascend" : "descend");
  };

  const clearSort = () => {
    setSortType("");
    setSortDirection("");
  };

  if (error) return 'An error has occurred: ' + error.message

  return (
      <>
        <Space style={{marginBottom: 16}}>
          <Button onClick={() => handleSort("name")}>
            Sort by Name {sortType === "name" && (sortDirection === "ascend" ? <UpOutlined/> :
              <DownOutlined/>)}
          </Button>
          <Button onClick={() => handleSort("price")}>
            Sort by Price {sortType === "price" && (sortDirection === "ascend" ? <UpOutlined/> :
              <DownOutlined/>)}
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
              pageSize: pageSize,
              current: currentPage,
              total: menuPage?.totalElements,
              onChange: handlePageChange,
              showSizeChanger: true,
              showQuickJumper: true,
              showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
              responsive: true,
            }}
            dataSource={sortedData()}
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
      </>
  );
};

export default RestaurantMenuList;