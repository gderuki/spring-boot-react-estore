// Node modules
import React, { Component } from 'react';

// Custom Modules
import { withApolloClient } from 'hoc/withApolloClient';
import AttributeService from 'services/AttributeService';
import { toKebabCase } from 'helpers/toKebabCase';

// Styles/CSS
import './AttributeSet.css';

class AttributeSet extends Component {
  constructor(props) {
    super(props);

    this.state = {
      selectedAttributes: {},
      attributeSets: [],
    };
  }

  render() {
    const { attributeSets } = this.state;
    return (
      <div>
        {Object.values(attributeSets).map((attributeSet) => (
          <div key={attributeSet.type}>
            <h2 className='attribute-heading'>{attributeSet.name}:</h2>
            {this.renderAttributeSet(attributeSet)}
          </div>
        ))}
      </div>
    );
  }

  componentDidMount() {
    const { apolloClient, productId, selectedAttributes } = this.props;

    AttributeService.fetchAttributeSets(apolloClient, productId)
      .then(attributeSets => {
        this.setState({ attributeSets });
        if (attributeSets.length === 0 && typeof this.props.onAllAttributesSelected === 'function') {
          this.props.onAllAttributesSelected(true);
        }
      });

    if (!selectedAttributes || Object.keys(selectedAttributes).length === 0) {
      return; // exiting, prop not provided
    }

    const flattenedAttributes = AttributeService.flattenSelectedAttributes(selectedAttributes);
    this.setState({
      selectedAttributes: flattenedAttributes,
    }, () => {
      Object.entries(flattenedAttributes).forEach(([attributeType, attributeValue]) => {
        this.selectAttribute(attributeValue, attributeType);
      });
    });
  }

  selectAttribute = (attributeValue, attributeType) => {
    if (this.props.noClick || this.props.noClickShallow) return;

    const updatedAttributes = AttributeService.updateSelectedAttributes(
      this.state.selectedAttributes,
      attributeValue,
      attributeType
    );

    this.setState({ selectedAttributes: updatedAttributes }, () => {
      if (this.props.onAllAttributesSelected) {
        this.checkAllAttributesSelected();
      }

      if (this.props.onAttributeSelect) {
        this.props.onAttributeSelect(updatedAttributes);
      }
    });
  };

  checkAllAttributesSelected = () => {
    const { attributeSets, selectedAttributes } = this.state;

    let allSelected = true;

    attributeSets.forEach(attributeSet => {
      if (!selectedAttributes[attributeSet.id]) {
        allSelected = false;
      }
    });

    if (allSelected) {
      this.props.onAllAttributesSelected(true);
    }
  }

  renderAttributeItem(attribute, attributeType) {
    const { noClick, small, dataTestIdCart } = this.props;
    const isSelected = this.state.selectedAttributes[attributeType] === attribute.value;

    const attributeNameKebab = toKebabCase(attributeType, false);
    const attributeValueKebab = toKebabCase(attribute.displayValue, true);
    const dataTestId = `product-attribute-${attributeNameKebab}-${attributeValueKebab}` + (isSelected ? '-selected' : '');
    
    const itemClass = [
      attribute.id === 'Color' ? 'item-color' : 'item-text',
      isSelected ? 'active' : '',
      noClick ? 'noclick' : '',
      small ? 'small' : '',
    ].join(' ').trim();

    const commonProps = {
      className: itemClass,
      onClick: () => this.selectAttribute(attribute.value, attributeType),
      ...({ 'data-testid': dataTestId }),
    };

    return (
      <div
        key={attribute.id}
        {...commonProps}
        style={attribute.id === 'Color' ? { backgroundColor: attribute.value } : {}}
      >
        {attribute.id !== 'Color' && attribute.displayValue}
      </div>
    );
  }

  renderAttributeSet(attributeSet) {
    const { dataTestIdCart, dataTestIdPDP } = this.props;
    let dataTestId = {};
    if (dataTestIdPDP) {
      const attributeSetNameKebabPDP = toKebabCase(attributeSet.id, false);
      dataTestId = { 'data-testid': `product-attribute-${attributeSetNameKebabPDP}` };
    } else if (dataTestIdCart) {
      const attributeSetNameKebabCart = toKebabCase(attributeSet.id, false);
      dataTestId = { 'data-testid': `cart-item-attribute-${attributeSetNameKebabCart}` };
    }
    
    
    return (
      <div
        key={attributeSet.id}
        className="item-container"
        {...dataTestId}
      >
        {attributeSet.items.map(item => this.renderAttributeItem(item, attributeSet.id))}
      </div>
    );
  }
}

export default withApolloClient(AttributeSet);