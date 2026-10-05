import React, { useState } from 'react';
import api from '../services/api';
import './Login.css' ; // ייבוא העיצוב הנפרד

const Login = () => {
  // ניהול הסטייטים של הטופס והתשובות מהשרת
  const [userId, setUserId] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');
    setIsLoading(true);

    try {
      // שליחת הנתונים לשרת ה-Java בפורמט שה-UsersController מצפה לקבל
      const response = await api.post('/api/users/login', {
        userId,
        phoneNumber,
      });

      // שמירת ה-JWT בזיכרון ה-localStorage
      localStorage.setItem('token', response.data.token);
      
      setMessage(response.data.message);
    } catch (err) {
      setError(err.response?.data || 'שגיאה בחיבור לשרת');
    } finally {
      setIsLoading(false); // החזרת הכפתור למצב פעיל
    }
  };

  return (
    <div className="login-container">
      <h2 className="login-title">התחברות למערכת</h2>

      {message && <div className="alert-message success">{message}</div>}
      {error && <div className="alert-message error">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">מספר מזהה (ID):</label>
          <input
            type="text"
            className="form-input"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            required
            disabled={isLoading}
          />
        </div>

        <div className="form-group">
          <label className="form-label">מספר טלפון:</label>
          <input
            type="text"
            className="form-input"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            required
            disabled={isLoading}
          />
        </div>

        <button type="submit" className="submit-button" disabled={isLoading}>
          {isLoading ? 'מתחבר...' : 'התחבר'}
        </button>
      </form>
    </div>
  );
};

export default Login;