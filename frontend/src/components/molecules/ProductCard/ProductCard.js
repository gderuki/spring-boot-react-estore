// Node modules
import React from 'react';
import { withRouter, Link } from 'react-router-dom';

// Custom Modules
import Button from 'atoms/Button';
import ProductTitle from 'atoms/ProductTitle';
import ProductPrice from 'atoms/ProductPrice';
import ProductImage from 'atoms/ProductImage';
import AddToCartIcon from 'icons/AddToCardIcon';
import { getImageUrl, getPriceInCurrency } from 'helpers/productHelpers';
import { addToCartHandler } from 'helpers/addToCartHandler';
import withCart from 'hoc/withCart';
import { withApolloClient } from 'hoc/withApolloClient';
import AttributeService from 'services/AttributeService';

// Styles/CSS
import './ProductCard.css';

class ProductCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isHovered: false,
      attributeSets: [],
      selectedAttributes: {},
    };
  }

  render() {
    const { isHovered } = this.state;
    const { productDetails, productSlug } = this.props;
    const { id, inStock, name } = productDetails;

    return (
      <a
        data-testid={`product-${productSlug}`}
        href={`/product/${id}`}
        className="product-card"
        tabIndex="0"
        state={{ id }}
        onMouseEnter={() => this.setIsHovered(true)}
        onMouseLeave={() => this.setIsHovered(false)}
      >
        <div className="image-container">
          <ProductImage
            className={`product-image ${!inStock ? 'grey-out' : ''}`}
            imageUrl={getImageUrl(productDetails)}
            altText={name}
          />
          {!inStock && <div className="out-of-stock-overlay">Out of Stock</div>}
        </div>
        {inStock ? (
          <Button
            className="add-to-cart-button"
            icon={<AddToCartIcon />}
            onClick={(event) => {
              event.preventDefault();
              this.handleAddToCart();
            }}
            style={{
              display: isHovered ? 'block' : 'none'
            }}
          />
        ) : null}
        <ProductTitle title={name} />
        <ProductPrice price={getPriceInCurrency(productDetails)} />
      </a>
    );
  }

  componentDidMount() {
    const { apolloClient, productDetails } = this.props;
    AttributeService.fetchAttributeSets(apolloClient, productDetails.id)
      .then(attributeSets => {
        const selectedAttributes = attributeSets.reduce((acc, attributeSet) => {
          acc[attributeSet.name] = attributeSet.items[0].value;
          return acc;
        }, {});

        this.setState({ selectedAttributes });
      })
      .catch(error => {
        console.error("Failed to fetch attribute sets", error);
      });
  }

  handleAddToCart = () => {
    addToCartHandler(
      this.props.productDetails,
      this.state.selectedAttributes,
      this.props.addToCart,
      this.props.toggleCartOverlay
    );
  };

  navigateToProductDetail = (productId) => {
    this.props.history.push(`/product/${productId}`);
  };

  setIsHovered = (isHovered) => {
    this.setState({ isHovered });
  }
}

export default
  withApolloClient(
    withCart(
      withRouter(
        ProductCard
      )
    )
  );