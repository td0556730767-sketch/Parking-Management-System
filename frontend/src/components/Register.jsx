import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate, Link } from 'react-router-dom';
import api from '../services/api';
import { setCredentials } from '../store';
import Input from './ui/Input';
import Button from './ui/Button';
import Alert from './ui/Alert';
import './Auth.css';

const Register = () => {
  const [formData, setFormData] = useState({
    userId: '',
    fullName: '',
    phoneNumber: '',
    balance: 0,
    stars: 0,
  });
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleChange = (field, value) => {
    setFormData((prev) => ({
      ...prev,
      [field]: field === 'userId' ? Number(value) : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      const response = await api.post('/api/users/register', formData);

      if (response.data?.token) {
        dispatch(setCredentials({
          token: response.data.token,
          user: response.data.user || formData
        }));
      }

      alert('ההרשמה בוצעה בהצלחה!');
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || err.response?.data || 'שגיאה בביצוע ההרשמה');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="auth-card">
      <h2 className="auth-title">הרשמת משתמש חדש</h2>

      <Alert type="error" message={error} />

      <form onSubmit={handleSubmit}>
        <Input
          label="מספר מזהה (ID):"
          type="number"
          value={formData.userId}
          onChange={(e) => handleChange('userId', e.target.value)}
          required
          disabled={isLoading}
        />

        <Input
          label="שם מלא:"
          type="text"
          value={formData.fullName}
          onChange={(e) => handleChange('fullName', e.target.value)}
          required
          disabled={isLoading}
        />

        <Input
          label="מספר טלפון:"
          type="tel"
          value={formData.phoneNumber}
          onChange={(e) => handleChange('phoneNumber', e.target.value)}
          required
          disabled={isLoading}
        />

        <Button type="submit" isLoading={isLoading}>
          הירשם
        </Button>
      </form>

      <div className="auth-footer">
        כבר רשום? <Link to="/login">התחבר כאן</Link>
      </div>
    </div>
  );
};

export default Register;