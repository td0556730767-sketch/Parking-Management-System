import './ParkingGate.css';
import React, { useState } from 'react';
import api from '../services/api';
import Input from './ui/Input';
import Button from './ui/Button';
import Alert from './ui/Alert';
import './ParkingGate.css';

const ParkingGate = () => {
  const [licensePlate, setLicensePlate] = useState('');
  const [gateState, setGateState] = useState('closed');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleGate = async (action) => {
    setMessage('');
    setError('');
    setGateState('checking');

    try {
      const response = await api.post(
        `/api/transactions/${action}/${encodeURIComponent(licensePlate)}`
      );
      const data = response.data;

      if (action === 'start') {
        setMessage(
          `ברוכים הבאים! נא לנסוע לחנייה מספר ${data.parking?.spotId}`
        );
      } else {
        setMessage(`להתראות! סכום לתשלום: ${data.totalPayment} ₪`);
      }

      setGateState('open');
      setTimeout(() => {
        setGateState('closed');
        setLicensePlate('');
      }, 5000);
    } catch (err) {
      setGateState('closed');
      const data = err.response?.data;
      setError(typeof data === 'string' && data ? data : 'לא ניתן לבצע את הפעולה');
    }
  };

  const isBusy = gateState === 'checking';

  return (
    <div className="auth-card">
      <h2 className="auth-title">שער החניון</h2>

      <div className="parking-gate-simulation">
        <div className="barrier-post" />
        <div className={`barrier-arm ${gateState}`} />
      </div>

      <Alert type="success" message={message} />
      <Alert type="error" message={error} />

      <Input
        label="מספר רכב:"
        value={licensePlate}
        onChange={(e) => setLicensePlate(e.target.value)}
        disabled={isBusy}
      />

      <Button
        isLoading={isBusy}
        disabled={!licensePlate}
        onClick={() => handleGate('start')}
      >
        כניסה
      </Button>

      <div style={{ height: 8 }} />

      <Button
        disabled={isBusy || !licensePlate}
        onClick={() => handleGate('exit')}
      >
        יציאה
      </Button>
    </div>
  );
};

export default ParkingGate;