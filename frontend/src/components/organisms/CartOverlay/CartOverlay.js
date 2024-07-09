// Node Modules
import React, { Component } from 'react';

// Custom Modules
import CartTotal from 'atoms/CartTotal';
import AttributeSet from 'molecules/AttributeSet';
import { extractProductIdFromCompositeKey } from 'helpers/generateCompositeKey';
import Button from 'atoms/Button';
import PlusIcon from 'icons/PlusIcon';
import MinusIcon from 'icons/MinusIcon';

// Styles/CSS
import './CartOverlay.css';
import { withApolloClient } from 'hoc/withApolloClient';
import { handlePlaceOrder } from 'services/OrderService';

class CartOverlay extends Component {
  constructor(props) {
    super(props);
    this.state = {
      quantity: {},
      noclick: false,
    };
  }

  render() {
    const { items } = this.props;
    const totalQuantity = items.length;
    const itemText = items.length === 1 ? 'item' : 'items';
    // Cart total have to be presented as a total of all items currently in the cart, 
    // if none it shall still be present while showing 0 in total
    const cartTotal = items.length === 0 ? 0 : items.reduce((acc, item) => acc + (item.price * item.quantity), 0).toFixed(2);
    const isCartEmpty = totalQuantity === 0;

    return (
      <div
        data-testid="cart-overlay"
        className="cart-overlay"
      >
        {totalQuantity > 0 ? (
          <h2 className='cart-heading'>{totalQuantity} {itemText}</h2>
        ) : (
          <h2 className='cart-heading'>No items</h2>
        )}
        <div className="products-list">
          {items.map((item, index) => (
            <React.Fragment key={index}>
              <div className="product-row">
                <div className="product-info-block">
                  <div className="product-title">{item.title}</div>
                  <div className="product-price">{item.price}</div>
                  <div className="attribute-sets">
                    <AttributeSet
                      small
                      noClickShallow
                      productId={extractProductIdFromCompositeKey(item.id)}
                      selectedAttributes={item.selectedAttributes}
                      dataTestIdCart
                    />
                  </div>
                </div>
                <div className="quantity-control">
                  <Button
                    className="quantity-btn"
                    onClick={() => this.props.addToCart(item)}
                    icon={<PlusIcon />}
                    dataTestId='cart-item-amount-increase'
                  />
                  <div className="quantity">{item.quantity}</div>
                  <Button
                    className="quantity-btn"
                    onClick={() => this.props.removeFromCart(item.id)}
                    icon={<MinusIcon />}
                    dataTestId='cart-item-amount-decrease'
                  />
                </div>
                <div className="image-block">
                  <img src={item.image} alt={item.title} />
                </div>
              </div>
              {index !== this.props.items.length - 1 && <hr className='product-delimiter' />}
            </React.Fragment>
          ))}
        </div>
        <CartTotal total={cartTotal} />
        <button
          className={`place-order-button ${isCartEmpty ? 'disabled' : ''}`}
          onClick={this.handlePlaceOrder}
          disabled={isCartEmpty}
        >
          Place Order
        </button>
      </div>
    );
  }

  handlePlaceOrder = async () => {
    handlePlaceOrder({
      apolloClient: this.props.apolloClient,
      items: this.props.items,
      clearCart: this.props.clearCart,
    });
  }


}

export default withApolloClient(CartOverlay);