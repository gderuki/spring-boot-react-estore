// Node modules
import React, { Component } from 'react';

// Styles/CSS
import './CartTotal.css';

class CartTotal extends Component {
  render() {
    const { total } = this.props;

    return (
      <div
        className="cart-total"
        data-testid='cart-total'
      >
        <span className='cart-total-label'>Total:</span>
        <span className='cart-total-value'>{total}</span>
      </div>
    );
  }
}

export default CartTotal;