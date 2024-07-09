// Node modules
import React, { Component } from 'react';

// Styles/CSS
import './ProductTitle.css';

class ProductTitle extends Component {
    render() {
        const { title } = this.props;
        return <h2 className='product-card-heading'>{title}</h2>;
    }
}

export default ProductTitle;