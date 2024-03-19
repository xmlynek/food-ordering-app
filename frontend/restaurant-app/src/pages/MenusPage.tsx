import {Layout, message, Typography} from "antd";
import React from "react";
import MenuList from "../components/Menu/MenuList.tsx";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {Outlet, useParams} from "react-router-dom";
import {MenuItem} from "../model/restaurant.ts";
import {
  deleteRestaurantMenuItem,
  fetchRestaurantMenuItems
} from "../client/restaurantMenuItemsApiClient.ts";

const {Title} = Typography;
const {Content} = Layout;

const MenusPage: React.FC = () => {
  const queryClient = useQueryClient();
  const params = useParams();

  const restaurantId = params.id as string;

  const {mutate: deleteMenu} = useMutation({
    mutationFn: deleteRestaurantMenuItem.bind(null, restaurantId), onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['menus']});
      message.success('Menu item deleted successfully');
    }
  });

  const {
    data: menus,
    error,
    isPending,
  } = useQuery<MenuItem[], Error>({
    queryKey: ['menus', restaurantId],
    queryFn: fetchRestaurantMenuItems.bind(null, restaurantId)
  });

  const handleDelete = async (id: string) => {
    console.log(`Deleting item with id: ${id}`);

    deleteMenu(id);
  };

  if (error) return 'An error has occurred: ' + error.message

  return (
      <Content>
        <Outlet/>
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of menus</Title>
        {/*</ Space>*/}
        <MenuList deleteHandler={handleDelete} menus={menus} isPending={isPending}/>

      </Content>
  );
};

export default MenusPage;