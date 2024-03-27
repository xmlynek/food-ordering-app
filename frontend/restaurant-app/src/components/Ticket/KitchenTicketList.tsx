import React from "react";
import {List} from "antd";
import {BasicKitchenTicketRestDTO} from "../../model/kitchenTicket.ts";
import {useNavigate} from "react-router-dom";

interface OrderListProps {
  kitchenTickets: BasicKitchenTicketRestDTO[] | undefined;
  isPending: boolean;
}

const KitchenTicketList: React.FC<OrderListProps> = ({kitchenTickets, isPending}: OrderListProps) => {
  const navigate = useNavigate();

  return (
      <List
          loading={isPending}
          itemLayout="horizontal"
          pagination={{position: 'bottom', align: 'start', pageSize: 10}}
          dataSource={kitchenTickets}
          renderItem={ticket => (
              <List.Item onClick={() => {navigate(`${ticket.id}`)}}>
                <List.Item.Meta
                    title={
                      <p>{ticket.id}</p>}
                    description={<p>Status: {ticket.status} CreatedAt: {ticket.createdAt} TotalPrice: {ticket.totalPrice}€</p>}
                />
              </List.Item>
          )}
      />
  );
};

export default KitchenTicketList;
