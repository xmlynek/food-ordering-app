import React from "react";
import {Avatar, List} from "antd";
import {OrderTicket} from "../../model/orderTicket.ts";



interface OrderListProps {
  orderTickets: Array<OrderTicket>;
  isPending: boolean;
}

const OrderTicketList: React.FC<OrderListProps> = ({orderTickets, isPending}: OrderListProps) => {

  return (
      <List
          loading={isPending}
          itemLayout="horizontal"
          pagination={{position: 'bottom', align: 'start', pageSize: 10}}
          dataSource={orderTickets}
          renderItem={item => (
              <List.Item onClick={() => {}}>
                <List.Item.Meta
                    avatar={<Avatar/>}
                    title={
                      <p>{item.id}</p>} // Replace href with a link to the restaurant detail page
                    description={<p>Status: {item.status} CreatedAt: {item.createdAt} TotalPrice: {item.totalPrice}€</p>}
                />
              </List.Item>
          )}
      />
  );
};

export default OrderTicketList;
