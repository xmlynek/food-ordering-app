import {Button, Col, Form, Input, Row} from "antd";
import {CardElement, useElements, useStripe} from "@stripe/react-stripe-js";
import {StripeCardElementOptions, Token} from "@stripe/stripe-js";
import {CheckoutFormValues} from "../../model/checkout.ts";
import React from "react";

const cardElementOptions: StripeCardElementOptions = {
  hidePostalCode: true,
  style: {
    base: {
      color: "#32325d",
      fontFamily: 'Arial, sans-serif',
      fontSmoothing: "antialiased",
      fontSize: "16px",
      "::placeholder": {
        color: "#7b858e"
      },
      iconColor: '#000000', // Customize the color of icons in the CardElement
    },
    invalid: {
      color: "#ff2d00",
      iconColor: "#ff2d00"
    }
  }
};

interface CheckoutFormProps {
  onSubmit: (values: CheckoutFormValues, token: Token) => void;
  totalPrice: string;
}

const CheckoutForm: React.FC<CheckoutFormProps> = ({
                                                     onSubmit,
                                                     totalPrice,
                                                   }: CheckoutFormProps) => {
  const [form] = Form.useForm<CheckoutFormValues>();
  const elements = useElements();
  const stripe = useStripe();

  const handleSubmit = async (values: CheckoutFormValues) => {
    if (!stripe || !elements) {
      return;
    }

    const cardElement = elements.getElement(CardElement);

    if (cardElement) {
      const {error, token} = await stripe.createToken(cardElement, {
        currency: 'eur',
        address_country: values.country,
        address_city: values.city,
        address_line1: values.addressLine,
        address_zip: values.postalCode,
        name: values.name,
      });

      if (error) {
        console.log('[error]', error);
      } else {
        // console.log('[PaymentMethod]', paymentMethod);
        console.log('[token]', token);
        onSubmit(values, token);
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
              <Input placeholder="Town"/>
            </Form.Item>
          </Col>
          <Col xs={24} sm={12}>
            <Form.Item name="country" label="Country"
                       rules={[{required: true, message: 'Please input your country!'}]}>
              <Input placeholder="Country"/>
            </Form.Item>
            <Form.Item name="addressLine" label="Address Line 1"
                       rules={[{required: true, message: 'Please input your address!'}]}>
              <Input placeholder="123 Any Street"/>
            </Form.Item>
            <Form.Item name="postalCode" label="Postal Code"
                       rules={[{required: true, message: 'Please input your postal code!'}]}>
              <Input placeholder="ABC 123"/>
            </Form.Item>
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
                style={{marginLeft: '8px'}}>({totalPrice}€)</strong>
            </Button>
          </Col>
        </Row>
      </Form>
  );
};

export default CheckoutForm;