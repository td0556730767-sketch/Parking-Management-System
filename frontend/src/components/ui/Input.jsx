import React from 'react';

const Input = ({ label, type = 'text', value, onChange, required = false, disabled = false, placeholder = '' }) => {
  return (
    <div className="form-group">
      {label && <label className="form-label">{label}</label>}
      <input
        type={type}
        className="form-input"
        value={value}
        onChange={onChange}
        required={required}
        disabled={disabled}
        placeholder={placeholder}
      />
    </div>
  );
};

export default Input;