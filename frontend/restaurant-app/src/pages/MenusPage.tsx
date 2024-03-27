import {Layout, message, Typography} from "antd";
import React from "react";
import MenuList from "../components/Menu/MenuList.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {Outlet, useParams} from "react-router-dom";
import {
  deleteRestaurantMenuItem,
} from "../client/restaurantMenuItemsApiClient.ts";

const {Title} = Typography;
const {Content} = Layout;

const MenusPage: React.FC = () => {
  const queryClient = useQueryClient();
  const params = useParams();

  const restaurantId = params.id as string;

  const {mutate: deleteMenu} = useMutation({
    mutationFn: deleteRestaurantMenuItem.bind(null, restaurantId), onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['menus', restaurantId]});
      message.success('Menu item deleted successfully');
    }
  });

  const handleDelete = async (id: string) => {
    console.log(`Deleting item with id: ${id}`);

    deleteMenu(id);
  };


  return (
      <Content>
        <Outlet/>
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of menus</Title>
        {/*</ Space>*/}
        <MenuList deleteHandler={handleDelete}/>

      </Content>
  );
};

export default MenusPage;