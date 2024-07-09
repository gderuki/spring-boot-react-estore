// Node Modules
import React, { Component } from 'react';

class ProductImage extends Component {
  render() {
    const { className, imageUrl, altText = 'Product Image' } = this.props;
    return (
      <img
        className={className}
        src={imageUrl}
        alt={altText}
      />
    );
  }
}

export default ProductImage;