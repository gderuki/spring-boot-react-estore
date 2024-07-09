import React, { Component } from 'react';

class ProductPrice extends Component {
    render() {
        const { price } = this.props;
        return <p>${price.toFixed(2)}</p>;
    }
}

export default ProductPrice;