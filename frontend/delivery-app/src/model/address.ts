export interface Address {
  street: string;
  postalCode: string;
  city: string;
  country: string;
}

export const getAddressAsString = (address: Address): string => {
  return `${address.street}, ${address.city}, ${address.postalCode}, ${address.country}`;
}