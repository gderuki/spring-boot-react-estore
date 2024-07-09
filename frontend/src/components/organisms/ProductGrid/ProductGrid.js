// Node Modules
import React from 'react';

// Custom Modules
import ProductCard from 'molecules/ProductCard';

// Styles/CSS
import './ProductGrid.css';

class ProductGrid extends React.Component {
  render() {
    const { products } = this.props;

    return (
      <div className='container-style'>
        {products.map(product => {
          return (
            <ProductCard
              key={product.id}
              productDetails={product}
              productSlug={product.name.toLowerCase().replace(/ /g, '-')}
              toggleCartOverlay={this.props.toggleCartOverlay}
            />
          );
        })}
      </div>
    );
  }
}

export default ProductGrid;