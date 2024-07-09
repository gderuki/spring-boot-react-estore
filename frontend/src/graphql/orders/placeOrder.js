// insertOrder(products: { productId: null, quantity: null, attributes: null })
import { gql } from '@apollo/client';

export const PLACE_ORDER_MUTATION = gql`
  mutation PlaceOrder($products: [ProductInput]!) {
    insertOrder(products: $products)
  }
`;

