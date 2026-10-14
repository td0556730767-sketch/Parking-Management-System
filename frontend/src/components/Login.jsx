import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate, Link } from 'react-router-dom';
import api from '../services/api'; // <--- תיקון הנתיב ל-api
import { setCredentials } from '../store';
import Input from './ui/Input';
import Button from './ui/Button';
import Alert from './ui/Alert';
import './Auth.css';

const Login = () => {
  const [userId, setUserId] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');
    setIsLoading(true);

    try {
      const response = await api.post('/api/users/login', {
        userId: Number(userId),
        phoneNumber,
      });

      if (response.data?.token) {
        dispatch(
          setCredentials({
            token: response.data.token,
            user: response.data.user || { userId, phoneNumber },
          })
        );
      }

      setMessage(response.data?.message || 'התחברת בהצלחה');
      setTimeout(() => navigate('/dashboard'), 1000);
    } catch (err) {
      setError(
        err.response?.data?.message || err.response?.data || 'שגיאה בחיבור לשרת'
      );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="auth-card">
      <h2 className="auth-title">התחברות למערכת</h2>

      <Alert type="success" message={message} />
      <Alert type="error" message={error} />

      <form onSubmit={handleSubmit}>
        <Input
          label="מספר מזהה (ID):"
          type="number"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
          disabled={isLoading}
        />

        <Input
          label="מספר טלפון:"
          type="tel"
          value={phoneNumber}
          onChange={(e) => setPhoneNumber(e.target.value)}
          required
          disabled={isLoading}
        />

        <Button type="submit" isLoading={isLoading}>
          התחבר
        </Button>
      </form>

      <div className="auth-footer">
        משתמש חדש? <Link to="/register">הירשם כאן</Link>
      </div>
    </div>
  );
};

export default Login;