import {CardElement, useElements, useStripe} from "@stripe/react-stripe-js";


const PaymentForm: React.FC = () => {
  const stripe = useStripe();
  const elements = useElements();

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!stripe || !elements) {
      // Stripe.js has not yet loaded; disable form submission until it does.
      return;
    }

    const cardElement = elements.getElement(CardElement);

    if (cardElement) {
      const { error, token } = await stripe.createToken(cardElement);

      if (error) {
        console.log('[error]', error);
      } else {
        console.log('[Token]', token);
        // Here, you'd typically send the token to your server to make a charge
      }
    }
  };

  return (
      <form onSubmit={handleSubmit}>
        <CardElement />
        <button type="submit" disabled={!stripe}>Pay</button>
      </form>
  );
};

export default PaymentForm;