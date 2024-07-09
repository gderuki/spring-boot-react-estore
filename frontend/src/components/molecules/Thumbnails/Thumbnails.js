import React from 'react';
import './Thumbnails.css';
import SquareImage from 'atoms/SquareImage';

class Thumbnails extends React.Component {
  render() {
    const { images, selectedImageIndex, setSelectedImageIndex } = this.props;
    return (
      <div className="thumbnails">
        {images && images.map((image, index) => (
          <SquareImage
            key={index}
            imageUrl={image}
            className={`thumbnail${index === selectedImageIndex ? ' selected' : ''}`}
            onClick={() => setSelectedImageIndex(index)}
          />
        ))}
      </div>
    );
  }
}

export default Thumbnails;