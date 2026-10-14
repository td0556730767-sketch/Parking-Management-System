import React from 'react';

const Alert = ({ type = 'error', message }) => {
  if (!message) return null;
  return <div className={`alert-message ${type}`}>{message}</div>;
};

export default Alert;