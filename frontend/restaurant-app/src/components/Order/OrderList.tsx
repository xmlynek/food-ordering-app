import React from "react";
import {List} from "antd";
import {Order} from "../../model/order.ts";



interface OrderListProps {
  orders: Array<Order>;
}

const OrderList: React.FC<OrderListProps> = ({orders}: OrderListProps) => {

  return (
      <List
          itemLayout="horizontal"
          pagination={{position: 'bottom', align: 'start', pageSize: 5}}
          dataSource={orders}
          renderItem={item => (
              <List.Item>
                {/*<List.Item.Meta*/}
                {/*    avatar={<Avatar src={item.logo}/>}*/}
                {/*    title={*/}
                {/*      <p>{item.name}</p>} // Replace href with a link to the restaurant detail page*/}
                {/*    description={item.description}*/}
                {/*/>                */}
                <p>order</p>
                <List.Item.Meta
                    description={item.id}
                />
              </List.Item>
          )}
      />
  );
};

export default OrderList;