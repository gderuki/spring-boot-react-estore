import React from 'react';
import { CartContext } from 'contexts/CartContext';

const withCart = WrappedComponent => {
  return class extends React.Component {
    render() {
      return (
        <CartContext.Consumer>
          {cartContext => <WrappedComponent {...this.props} {...cartContext} />}
        </CartContext.Consumer>
      );
    }
  };
};

export default withCart;