import React, {useEffect} from 'react';
import {Form, message, Modal, Skeleton} from 'antd';
import {useNavigate, useParams} from 'react-router-dom';
import MenuItemFormSkeleton from '../components/Menu/MenuItemFormSkeleton.tsx';
import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import {MenuItem, MenuItemFormValues} from '../model/restaurant.ts';
import {
  fetchRestaurantMenuItem,
  updateRestaurantMenuItem
} from "../client/restaurantMenuItemsApiClient.ts";

const ModifyMenuPage: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<MenuItemFormValues>();
  const params = useParams();
  const navigate = useNavigate();

  const restaurantId = params.id as string;
  const menuId = params.menuId as string;


  const {data: menuItemData, isPending: isFetchPending} = useQuery<MenuItem, Error>({
    queryKey: ['menuItemById'],
    queryFn: fetchRestaurantMenuItem.bind(null, restaurantId, menuId)
  });


  const {mutateAsync, isPending} = useMutation({
    mutationFn: updateRestaurantMenuItem.bind(null, restaurantId, menuId),
    onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['menus']});
      message.success('Menu updated successfully!');
      navigate(`../`);
    },
  });

  useEffect(() => {
    // Populate the form with the fetched menu item data once it's available
    if (menuItemData) {
      form.setFieldsValue({
        name: menuItemData.name,
        description: menuItemData.description,
        price: +menuItemData.price,
      });
    }
  }, [menuItemData, form]);

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      await mutateAsync(values);
    } catch (errorInfo) {
      console.log('Failed:', errorInfo);
      message.error('Menu update failed!');
    }
  };

  const handleCancel = () => {
    navigate(`../`);
  };

  return (
      <Modal
          title={<p>Modify menu item <strong>{menuItemData && menuItemData.name}</strong></p>}
          open={true}
          onOk={handleOk}
          confirmLoading={isPending}
          onCancel={handleCancel}
          destroyOnClose={true}
          maskClosable={false}
      >
        {isFetchPending ? <Skeleton active/> :
            <MenuItemFormSkeleton form={form}/>}
      </Modal>
  );
};

export default ModifyMenuPage;