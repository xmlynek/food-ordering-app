import {Card, Typography} from "antd";
import CheckoutForm from "../components/Checkout/CheckoutForm.tsx";
import {Elements} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";

const {Title} = Typography;

const stripePromise = loadStripe('pk_test_51Oc4G2Hg2RuOlHnDqgW42ddAokEcXPW0MSOEqtKbMUCKGPTNAdXC9ui5BapPvOr59BXFdSeaObNTOVYqEQUsOgO6001DCDN9Iw');


const CheckoutPage = () => {
  return (
      <Elements stripe={stripePromise}>
        <Card bordered={false} style={{ maxWidth: 1280, margin: '20px auto', padding: '20px' }}>
          <Title level={2} style={{ textAlign: 'center', marginBottom: '40px' }}>Checkout</Title>
          <CheckoutForm />
        </Card>
      </Elements>
  );
};

export default CheckoutPage;