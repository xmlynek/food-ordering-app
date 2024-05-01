import {Form, message, Modal} from "antd";
import React, {useEffect} from "react";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {
  Restaurant, RestaurantFormValues
} from "../../model/restaurant.ts";
import {updateRestaurant} from "../../client/restaurantApiClient.ts";
import RestaurantForm from "./RestaurantForm.tsx";


interface ModifyRestaurantModalProps {
  restaurant: Restaurant;
  isVisible: boolean;
  onHideModal: () => void;
}

const ModifyRestaurantModal: React.FC<ModifyRestaurantModalProps> = ({
                                                                       restaurant,
                                                                       isVisible,
                                                                       onHideModal,
                                                                     }: ModifyRestaurantModalProps) => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<RestaurantFormValues>();

  const {
    mutateAsync: updateRestaurantMutation,
    isPending: isUpdatePending,
  } = useMutation({
    mutationFn: updateRestaurant.bind(null, restaurant.id), onSuccess: async () => {
      await message.success('Restaurant updated successfully!');
      await queryClient.refetchQueries({queryKey: ['restaurantById', restaurant.id]});
    },
    onError: async (error) => {
      await message.error('Restaurant update failed: ' + error.message);
    }
  });

  useEffect(() => {
    if (restaurant) {
      form.setFieldsValue({
        name: restaurant.name,
        description: restaurant.description,
        isAvailable: restaurant.isAvailable,
        address: {
          street: restaurant.address.street,
          city: restaurant.address.city,
          postalCode: restaurant.address.postalCode,
          country: restaurant.address.country,
        },
      });
    }
  }, [restaurant, form]);

  const handleModalOk = async () => {
    try {
      const values = await form.validateFields();
      // @ts-ignore
      await updateRestaurantMutation(values);
      onHideModal();
    } catch (errorInfo) {
      console.log('Failed:', errorInfo);
      await message.error('Restaurant update failed!');
    }
  }

  // if (error) return 'An error has occurred: ' + error.message

  return (
      <Modal
          title={`Modify restaurant '${restaurant.name}'`}
          open={isVisible}
          confirmLoading={isUpdatePending}
          onOk={handleModalOk}
          onCancel={onHideModal}
          destroyOnClose={true}
          maskClosable={true}
      >
        <RestaurantForm form={form}/>
      </Modal>
  );
};

export default ModifyRestaurantModal;