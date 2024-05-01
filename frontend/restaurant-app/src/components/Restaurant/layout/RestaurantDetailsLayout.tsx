import {Col, Layout, Menu, Row} from "antd";
import React from "react";
import {Link, useLocation} from "react-router-dom";
import {CheckCircleOutlined, UnorderedListOutlined} from "@ant-design/icons";
import {KitchenTicketStatus} from "../../../model/enum.ts";


interface RestaurantLayoutProps {
  children: React.ReactNode;
}

const RestaurantDetailsLayout: React.FC<RestaurantLayoutProps> = ({children}: RestaurantLayoutProps) => {

  const location = useLocation();

  const getSelectedKeys = () => {
    const path = location.pathname;
    const search = location.search;
    if (path.includes('/menu/add')) return ['addMenu'];
    if (path.includes('/menu')) return ['actualMenu'];
    if (path.includes('/tickets') && search.includes(`ticketStatus=${KitchenTicketStatus.PREPARING}`)) return ['preparingTickets'];
    if (path.includes('/tickets') && search.includes(`ticketStatus=${KitchenTicketStatus.READY_FOR_DELIVERY}`)) return ['readyTickets'];
    if (path.includes('/tickets') && search.includes(`ticketStatus=${KitchenTicketStatus.FINISHED}`)) return ['finishedTickets'];
    if (path.includes('/tickets')) return ['activeTickets'];
    return ['actualMenu'];
  };

  const menuItems = [
    {
      key: 'menu',
      icon: <CheckCircleOutlined/>,
      label: 'Menu',
      children: [
        {
          key: 'actualMenu',
          label: <Link to="menu" className="text-decoration-none">Menu</Link>,
        },
        {
          key: 'addMenu',
          label: <Link to="menu/add" className="text-decoration-none">Add Menu</Link>,
        },
      ],
    },
    {
      key: 'tickets',
      icon: <UnorderedListOutlined/>,
      label: 'Tickets',
      children: [
        {
          key: 'activeTickets',
          label: <Link to="tickets" className="text-decoration-none">All Tickets</Link>,
        },
        {
          key: 'preparingTickets',
          label: <Link to={`tickets?ticketStatus=${KitchenTicketStatus.PREPARING}`}
                       className="text-decoration-none">Tickets Preparing</Link>,
        },
        {
          key: 'readyTickets',
          label: <Link to={`tickets?ticketStatus=${KitchenTicketStatus.READY_FOR_DELIVERY}`}
                       className="text-decoration-none">Tickets Ready</Link>,
        },
        {
          key: 'finishedTickets',
          label: <Link to={`tickets?ticketStatus=${KitchenTicketStatus.FINISHED}`}
                       className="text-decoration-none">Tickets Finished</Link>,
        },
      ],
    },
  ];

  return (
      <Layout style={{
        marginTop: '3rem',
        boxShadow: 'rgba(0, 0, 0, 0.36) 0px 22px 70px 4px',
        backgroundColor: 'rgba(0, 0, 0, 0.11)'
      }}>
        <Row>
          <Col xs={24} sm={24} md={7} lg={6}>
            <Menu
                theme="dark"
                mode="inline"
                defaultSelectedKeys={getSelectedKeys()}
                defaultOpenKeys={['menu', 'tickets']}
                style={{height: '100%'}}
                items={menuItems}
            />
          </Col>
          <Col xs={24} sm={24} md={17} lg={{span: 17, offset: 1}}>
            {children}
          </Col>
        </Row>
      </Layout>
  );
};

export default RestaurantDetailsLayout;