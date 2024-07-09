import React, { Component } from 'react';

class CartIcon extends Component {
  render() {
    return (
      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M3 3H5L6 13H18L19 3H21" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
        <circle cx="7" cy="20" r="1" fill="currentColor" />
        <circle cx="17" cy="20" r="1" fill="currentColor" />
      </svg>
    );
  }
}

export default CartIcon;