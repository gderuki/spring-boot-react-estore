import { PLACE_ORDER_MUTATION } from 'graphql/orders/placeOrder';
import { extractProductIdFromCompositeKey } from 'helpers/generateCompositeKey';

export const handlePlaceOrder = async ({ apolloClient, items, clearCart }) => {
  const orderItems = items.map(item => {
    const attributesArray = Object.entries(item.selectedAttributes).map(([key, value]) => ({
      key,
      value: typeof value === 'string' ? value : JSON.stringify(value),
    }));
    return {
      productId: extractProductIdFromCompositeKey(item.id),
      quantity: item.quantity,
      attributes: attributesArray,
    };
  });

  try {
    const result = await apolloClient.mutate({
      mutation: PLACE_ORDER_MUTATION,
      variables: { products: orderItems },
    });

    const orderPlaced = result.data;

    if (orderPlaced) {
      console.log('Order successfully submitted!');
      clearCart();
    } else {
      console.error("Order submission failed.");
    }
  } catch (error) {
    console.error("Error placing order:", error);
  }
};