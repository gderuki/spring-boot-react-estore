// Custom modules
import { generateCompositeKey } from "./generateCompositeKey";
import { getImageUrl, getPriceInCurrency } from "./productHelpers";

export const addToCartHandler = (
  productDetails,
  selectedAttributes,
  addToCartProp,
  toggleCartOverlayProp
) => {
  if (productDetails.inStock === false) {
    return;
  }

  const payload = {
    id: generateCompositeKey(productDetails.id, selectedAttributes),
    title: productDetails.name,
    image: getImageUrl(productDetails),
    price: getPriceInCurrency(productDetails),
    selectedAttributes: selectedAttributes,
    quantity: 1,
  };

  addToCartProp(payload);
  toggleCartOverlayProp();
}