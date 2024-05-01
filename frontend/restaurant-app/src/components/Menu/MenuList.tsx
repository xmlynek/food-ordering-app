import React, {useEffect} from "react";
import {Button, Card, Col, Divider, Image, List, Row, Space, Tag, Typography} from "antd";
import {DeleteOutlined, DownOutlined, EditOutlined, UpOutlined} from "@ant-design/icons";
import {useNavigate, useParams} from "react-router-dom";
import {usePagination} from "../../hooks/usePagination.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {fetchRestaurantMenuItems} from "../../client/restaurantMenuItemsApiClient.ts";
import {MenuItem} from "../../model/menuItem.ts";

import styles from './MenuList.module.css';
import {useQuery} from "@tanstack/react-query";
import useSortableData from "../../hooks/useSortableData.ts";

const {Title, Paragraph} = Typography;

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

  const {
    items: sortedMenuItems,
    requestSort,
    sortConfig,
    clearSort
  } = useSortableData<MenuItem>(menuItemsPage?.content || []);


  if (error) return 'An error has occurred: ' + error.message

  return (
      <>
        <Space style={{marginBottom: 16}} wrap>
          <Button onClick={() => requestSort("isAvailable")}>
            Sort by
            Availability{sortConfig.key === "isAvailable" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={() => requestSort("price")}>
            Sort by Price {sortConfig.key === "price" && (sortConfig.direction === "ascend" ?
              <UpOutlined/> : <DownOutlined/>)}
          </Button>
          <Button onClick={clearSort}>Clear Sort</Button>
          <Divider />
        </Space>
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
            dataSource={sortedMenuItems}
            renderItem={item => (
                <List.Item key={item.id}>
                  <Card
                      actions={[
                        <Row justify={"space-evenly"}>
                          <Button key="list-modify" icon={<EditOutlined/>}
                                  onClick={() => handleModify(item.id)}>
                            Modify
                          </Button>
                          <Button key="list-delete" icon={<DeleteOutlined/>} danger
                                  onClick={() => deleteHandler(item.id)}>
                            Delete
                          </Button>
                        </Row>
                      ]}
                  >
                    <Row gutter={16}>
                      <Col xs={24} sm={24} md={11} lg={7} xl={6}>
                        <Image src={item.imageUrl} width={128} height={128}
                               style={{objectFit: 'cover'}}/>
                      </Col>
                      <Col xs={24} sm={24} md={13} lg={17} xl={18}>
                        <List.Item.Meta
                            title={
                              <div>
                                <Row>
                                  <Col xs={24} sm={24} md={18}>
                                    <Title level={4} style={{marginTop: 0}}>{item.name}</Title>
                                  </Col>
                                  <Col style={{textAlign: 'right'}} xs={24} sm={24} md={6}>
                                    {item.isAvailable ? <Tag color="green">Available</Tag> :
                                        <Tag color="red">Not Available</Tag>}
                                  </Col>
                                </Row>
                                <div style={{marginTop: "5px"}}>
                                  <Paragraph>Price: €{item.price.toFixed(2)}</Paragraph>
                                </div>
                              </div>
                            }
                            description={item.description}
                        />
                      </Col>
                    </Row>
                  </Card>
                </List.Item>
            )}
        />
      </>
  );
};

export default MenuList;