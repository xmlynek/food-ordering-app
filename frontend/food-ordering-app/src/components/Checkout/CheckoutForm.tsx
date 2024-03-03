import {Button, Col, Form, Input, message, Row} from "antd";
import {
  CardElement,
  useElements,
  useStripe
} from "@stripe/react-stripe-js";
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {useMutation} from "@tanstack/react-query";
import axios from "axios";

const cardElementOptions = {
  style: {
    base: {
      color: "#32325d",
      fontFamily: 'Arial, sans-serif',
      fontSmoothing: "antialiased",
      fontSize: "16px",
      "::placeholder": {
        color: "#aab7c4"
      },
      iconColor: '#c4f0ff', // Customize the color of icons in the CardElement
    },
    invalid: {
      color: "#fa755a",
      iconColor: "#fa755a"
    }
  }
};

const CheckoutForm = () => {
  const stripe = useStripe();
  const elements = useElements();
  const [form] = Form.useForm();
  const {calculateTotalPrice, basket} = useBasket();

  async function postOrder(orderData) {
    const response = await axios.post('http://localhost:8080/api/orders', orderData);
    return response.data;
  }

  const {mutateAsync, isLoading, isError, data, error} = useMutation({
    mutationKey: ['postOrder'],
    mutationFn: postOrder,
    onSuccess: async (data) => {
      // await queryClient.invalidateQueries({queryKey: ['order-tickets']});
      message.success(`Order ${data.id} created successfully`);
    }
  });


  const handleSubmit = async (values) => {
    if (!stripe || !elements) {
      return;
    }

    const cardElement = elements.getElement(CardElement);

    if (cardElement) {
      const {error, token} = await stripe.createToken(cardElement, {
        currency: 'eur',
        address_country: values.country,
        address_city: values.city,
        address_line1: values.line1,
        address_state: values.state,
        name: values.name,
      });


      if (error) {
        console.log('[error]', error);
      } else {
        // console.log('[PaymentMethod]', paymentMethod);
        console.log('[token]', token);
        // Process paymentMethod further (e.g., send to your server)
        await mutateAsync({
          customerId: "b4ccc7e2-fb81-4040-87fe-f04189bb21be", // TODO change with keycloak user
          restaurantId: basket?.[0].restaurantId,
          paymentToken: token?.id,
          address: {
            street: token?.card?.address_line1,
            postalCode: token?.card?.address_zip,
            city: token?.card?.address_city,
          },
          totalPrice: calculateTotalPrice().toFixed(2),
          items: basket.map(item => ({
            productId: item.id,
            quantity: item.quantity,
            price: item.price.toFixed(2)
          }))
        });
      }
    } else {
      console.log("CardElement not found");
    }
  };

  return (
      <Form form={form} layout="vertical" onFinish={handleSubmit} className="checkout-form">
        <Row gutter={24}>
          <Col xs={24} sm={12}>
            <Form.Item name="name" label="Name"
                       rules={[{required: true, message: 'Please input your name!'}]}>
              <Input placeholder="Jane Doe"/>
            </Form.Item>
            <Form.Item name="city" label="City"
                       rules={[{required: true, message: 'Please input your city!'}]}>
              <Input placeholder="Anytown"/>
            </Form.Item>
          </Col>
          <Col xs={24} sm={12}>
            <Form.Item name="country" label="Country"
                       rules={[{required: true, message: 'Please input your country!'}]}>
              <Input placeholder="Country"/>
            </Form.Item>
            <Form.Item name="line1" label="Address Line 1"
                       rules={[{required: true, message: 'Please input your address!'}]}>
              <Input placeholder="123 Any Street"/>
            </Form.Item>
            {/*<Form.Item name="postal_code" label="Postal Code"*/}
            {/*           rules={[{required: true, message: 'Please input your postal code!'}]}>*/}
            {/*  <Input placeholder="ABC 123"/>*/}
            {/*</Form.Item>*/}
          </Col>
          <Col xs={24}>
            <Form.Item label="Card Details"
                       rules={[{required: true, message: 'Please input your card details!'}]}>
              <CardElement options={cardElementOptions}/>
            </Form.Item>
          </Col>
          <Col xs={24} style={{textAlign: 'center'}}>
            <Button type="primary" htmlType="submit" disabled={!stripe} size="large">
              Confirm order <strong
                style={{marginLeft: '8px'}}>({calculateTotalPrice().toFixed(2)}€)</strong>
            </Button>
          </Col>
        </Row>
      </Form>
  );
};

export default CheckoutForm;