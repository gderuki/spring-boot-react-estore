import { gql } from "@apollo/client";

export const GET_PRODUCTS = gql`
    query GetProducts {
        getAllProducts {
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
