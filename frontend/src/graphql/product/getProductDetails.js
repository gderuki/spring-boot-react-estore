import { gql } from '@apollo/client';

export const GET_PRODUCT_DETAILS = gql`
    query GetProductDetails($id: String!) {
        getProduct(id: $id) {
            id
            name
            description
            inStock
            category
            gallery
            prices {
                amount
                currency {
                    label
                    symbol
                }
            }
            attributes {
                name
                items {
                    value
                    displayValue
                }
            }
        }
    }
`;

export const GET_ATTRIBUTE_SETS = gql`
    query GetProductSummary($id: String!) {
        getProduct(id: $id) {
            attributes {
                name
                items {
                    value
                    displayValue
                }
            }
        }
    }
`;