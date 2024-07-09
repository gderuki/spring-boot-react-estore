import React, { Component } from 'react';

// intentionally left without own styling
class Button extends Component {
  render() {
    const { label, onClick, icon, style, className, disabled, dataTestIdText } = this.props;
    const buttonProps = {
      onClick,
      style,
      className,
      disabled,
      ...(dataTestIdText && { 'data-testid': dataTestIdText })
    };

    return (
      <button {...buttonProps}>
        {icon && <span>{icon}</span>}
        {label && <span>{label}</span>}
      </button>
    );
  }
}

export default Button;