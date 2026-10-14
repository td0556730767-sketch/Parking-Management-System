import React from 'react';

const Button = ({ children, type = 'button', disabled = false, isLoading = false, onClick }) => {
  return (
    <button type={type} className="submit-button" disabled={disabled || isLoading} onClick={onClick}>
      {isLoading ? 'בטיפול...' : children}
    </button>
  );
};

export default Button;