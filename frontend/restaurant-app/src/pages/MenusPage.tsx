import {Layout, message, Typography} from "antd";
import React from "react";
import MenuList from "../components/Menu/MenuList.tsx";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {Outlet, useParams} from "react-router-dom";

const {Title} = Typography;
const {Content} = Layout;

const MenusPage: React.FC = () => {
  const queryClient = useQueryClient();
  const params = useParams();

  const fetchMenus = async (): Promise<MenuItem[]> => {
    const response = await fetch(`http://localhost:8085/api/restaurants/${params.id}/menu`);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  };
  const deleteMenuItemMutation = async (menuId: string): Promise<void> => {
    const response = await fetch(`http://localhost:8085/api/restaurants/${params.id}/menu/${menuId}`, {
      method: 'DELETE',
    });
    if (!response.ok) {
      throw new Error('Failed to delete the menu item');
    }
  }

  const {mutate: deleteMenu} = useMutation({
    mutationFn: deleteMenuItemMutation, onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['menus']});
      message.success('Menu item deleted successfully');
    }
  });

  const {
    data: menus,
    error,
    isPending,
  } = useQuery<MenuItem[], Error>({queryKey: ['menus'], queryFn: fetchMenus});

  const handleDelete = async (id: string) => {
    console.log(`Deleting item with id: ${id}`);

    deleteMenu(id);
    // Call API to delete the item
    // Or set local state to remove the item from the list
  };


  if (error) return 'An error has occurred: ' + error.message
  if (isPending) return 'Loading...'

  return (
      <Content>
        <Outlet/>
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        <Title style={{marginTop: 0}} level={2}>List of menus</Title>
        {/*</ Space>*/}
        <MenuList deleteHandler={handleDelete} menus={menus}/>

      </Content>
  );
};

export default MenusPage;