import React, { Component } from 'react';

// intentionally left without own styling
class SquareImage extends Component {
  render() {
    const { imageUrl, className, onClick } = this.props;
    return (
      <div
        className={className}
        onClick={onClick}
        style={{ backgroundImage: `url(${imageUrl})` }}
      />
    );
  }
}

export default SquareImage;