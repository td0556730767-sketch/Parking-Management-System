import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import api from '../services/api';
import Alert from './ui/Alert';

const Dashboard = () => {
  const { user } = useSelector((state) => state.auth);
  const [profile, setProfile] = useState(user);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!user?.userId) return;

    api
      .get(`/api/users/getById/${user.userId}`)
      .then((res) => setProfile(res.data))
      .catch(() => setError('לא הצלחנו לטעון את הפרטים'));
  }, [user?.userId]);

  return (
    <div className="auth-card">
      <h2 className="auth-title">שלום {profile?.fullName}</h2>

      <Alert type="error" message={error} />

      <p>נקודות זכות: <strong>{profile?.stars ?? 0}</strong></p>
      <p>יתרה: <strong>{profile?.balance ?? 0} ₪</strong></p>
    </div>
  );
};

export default Dashboard;