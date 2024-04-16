import {loadStripe} from "@stripe/stripe-js";

export const stripePromise = loadStripe(window.envVars.REACT_STRIPE_PUBLISHABLE_KEY);
