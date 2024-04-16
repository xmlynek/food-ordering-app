import React, {useEffect} from 'react';
import {Form, Image, message, Modal, Skeleton, Space, Switch} from 'antd';
import {useNavigate, useParams} from 'react-router-dom';
import MenuItemFormSkeleton from '../components/Menu/MenuItemFormSkeleton.tsx';
import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import {
  fetchRestaurantMenuItem,
  updateRestaurantMenuItem
} from "../client/restaurantMenuItemsApiClient.ts";
import {MenuItem, MenuItemFormValues} from "../model/menuItem.ts";
import UploadFormItem from "../components/UI/UploadFormItem.tsx";

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
        isAvailable: menuItemData.isAvailable,
        description: menuItemData.description,
        price: +menuItemData.price,
      });
    }
  }, [menuItemData, form]);

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      console.log(values);
      // @ts-ignore
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
            <MenuItemFormSkeleton form={form}>
              <Form.Item
                  label="Availability"
                  name="isAvailable"
                  valuePropName="checked"
                  initialValue={true}
              >
                <Switch/>
              </Form.Item>
              <Space direction={"vertical"} style={{marginBottom: '20px'}}>
                Current image
                <Image src={menuItemData?.imageUrl} alt={menuItemData?.name}
                       style={{
                         height: 'auto',
                         maxHeight: '150px',
                         width: '150px',
                         objectFit: 'cover'
                       }}/>
              </Space>
              <UploadFormItem form={form} required={false}/>
            </MenuItemFormSkeleton>}
      </Modal>
  );
};

export default ModifyMenuPage;