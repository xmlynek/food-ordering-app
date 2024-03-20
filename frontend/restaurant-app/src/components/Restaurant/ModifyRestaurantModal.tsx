import {Form, message, Modal} from "antd";
import React, {useEffect} from "react";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {RestaurantFormValues} from "../model/restaurant.ts";
import {
  BasicRestaurantRestDto,
  Restaurant
} from "../../model/restaurant.ts";
import {updateRestaurant} from "../../client/restaurantApiClient.ts";
import RestaurantForm from "./RestaurantForm.tsx";


interface ModifyRestaurantModalProps {
  restaurant: Restaurant;
  isVisible: boolean;
  hideModal: () => void;
}

const ModifyRestaurantModal: React.FC<ModifyRestaurantModalProps> = ({
                                                                       restaurant,
                                                                       isVisible,
                                                                       hideModal,
                                                                     }: ModifyRestaurantModalProps) => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<RestaurantFormValues>();

  const {
    mutateAsync: updateRestaurantMutation,
    isPending: isUpdatePending,
    error
  } = useMutation<BasicRestaurantRestDto, Error>({
    mutationFn: updateRestaurant.bind(null, restaurant.id), onSuccess: async () => {
      await message.success('Restaurant updated successfully!');
      await queryClient.refetchQueries(['restaurantById', restaurant.id]);
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
      await updateRestaurantMutation(values);
      hideModal();
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
          onCancel={hideModal}
          destroyOnClose={true}
          maskClosable={true}
      >
        <RestaurantForm form={form}/>
      </Modal>
  );
};

export default ModifyRestaurantModal;