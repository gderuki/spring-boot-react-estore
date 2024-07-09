// Node modules
import React, { Component } from 'react';


// Custom Modules
import ProductGrid from 'organisms/ProductGrid';
import { withApolloClient } from 'hoc/withApolloClient';
import { GET_PRODUCTS } from 'graphql/product/getProducts';

// Styles/CSS
import './ProductListing.css';

class ProductListing extends Component {
  constructor(props) {
    super(props);

    this.state = {
      products: [],
    };
  }

  componentDidMount() {
    const { apolloClient } = this.props;

    apolloClient
      .query({
        query: GET_PRODUCTS,
      }).then(result => {
      this.setState({ products: result.data.getAllProducts })
    })
      .catch(error => {
        console.error(error);
      });
  }

  render() {
    const { categoryName } = this.props.match.params;

    const filteredProducts = this.state.products.filter(product =>
      categoryName === 'all' || !categoryName ? true : product.category === categoryName
    );

    return (
      <div className="product-listing">
        <h1 className='product-title'>{categoryName || 'Product Listing'}</h1>
        <ProductGrid
          toggleCartOverlay={this.props.toggleCartOverlay}
          products={filteredProducts}
        />
      </div>
    );
  }
}

export default withApolloClient(ProductListing);