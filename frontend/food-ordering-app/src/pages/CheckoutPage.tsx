import {Card, message, Typography} from "antd";
import CheckoutForm from "../components/Checkout/CheckoutForm.tsx";
import {Elements} from "@stripe/react-stripe-js";
import {useBasket} from "../hooks/useBasketContext.tsx";
import {postOrder} from "../client/ordersApiClient.ts";
import {CheckoutFormValues} from "../model/checkout.ts";
import {useMutation} from "@tanstack/react-query";
import {Token} from "@stripe/stripe-js";
import keycloak from "../keycloak/keycloak.ts";
import {stripePromise} from "../stripe/stripe.ts";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const {Title} = Typography;


const CheckoutPage = () => {
  const {calculateTotalPrice, basket, clearBasket} = useBasket();
  const navigate = useNavigate();

  useEffect(() => {
    if (basket.length === 0) {
      navigate('/');
    }
  }, [basket, navigate]);

  // TODO: do something after order is created
  const {mutateAsync} = useMutation({
    mutationKey: ['postOrder'],
    mutationFn: postOrder,
    onSuccess: async (data) => {
      // await queryClient.invalidateQueries({queryKey: ['order-tickets']});
      message.success(`Order ${data.id} created successfully`);
    },
    onError: async (error) => {
      message.error('An error occurred while creating the order: ' + error.message);
    }
  });


  const handleSubmit = async (values: CheckoutFormValues, token: Token) => {
    await mutateAsync({
      customerId: keycloak!.subject || '',
      restaurantId: basket?.[0].restaurantId,
      paymentToken: token?.id,
      address: {
        street: token?.card?.address_line1 || values.addressLine,
        postalCode: token?.card?.address_zip || values.postalCode,
        city: token?.card?.address_city || values.city,
        country: token?.card?.address_country || values.country,
      },
      totalPrice: calculateTotalPrice().toFixed(2),
      items: basket.map(item => ({
        productId: item.id,
        quantity: item.quantity,
        price: item.price.toFixed(2)
      }))
    });
    clearBasket();
  };


  return (
      <Elements stripe={stripePromise}>
        <Card bordered={false} style={{maxWidth: 1280, margin: '20px auto', padding: '20px'}}>
          <Title level={2} style={{textAlign: 'center', marginBottom: '40px'}}>Checkout</Title>
          <CheckoutForm onSubmit={handleSubmit} totalPrice={calculateTotalPrice().toFixed(2)}/>
        </Card>
      </Elements>
  );
};

export default CheckoutPage;