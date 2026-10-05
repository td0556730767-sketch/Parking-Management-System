import axios from 'axios';

// יוצרים מופע אחיד שפונה לשרת ה-Java בפורט 8080
const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

// אופציונלי ומומלץ לעתיד: הוספת טוקן ה-JWT אוטומטית לכל בקשה שיוצאת לשרת
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;