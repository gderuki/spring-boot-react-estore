import USER_CONFIG from "constants/UserConfig";

export const getPriceInCurrency = (product, currencyLabel = USER_CONFIG.DEFAULT_CURRENCY) => {
  const priceItem = product.prices.find(price => price.currency.label === currencyLabel);
  return priceItem ? priceItem.amount : null;
}

export const getImageUrl = (product) => {
  return product.gallery.length > 0 ? product.gallery[0] : null;
}