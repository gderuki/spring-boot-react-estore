import React from 'react';
import './PriceTag.css';

class PriceTag extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <h2 className='price-tag-heading'>Price: </h2>
        <p className='price-tag-value'>{this.props.value}</p>
      </div>
    );
  }
}

export default PriceTag;