import { configureStore, createSlice } from '@reduxjs/toolkit';

// יצירת Slice (פרוסה) ניהול מצב עבור משתמש והתחברות
const authSlice = createSlice({
  name: 'auth',
  initialState: {
    token: localStorage.getItem('token') || null,
    user: null,
    isAuthenticated: !!localStorage.getItem('token'),
  },
  reducers: {//הפעולות
    setCredentials: (state, action) => {// פעולה שמעדכנת את הסטייט עם פרטי המשתמש והטוקן

      state.token = action.payload.token;
      state.user = action.payload.user;
      state.isAuthenticated = true;
      localStorage.setItem('token', action.payload.token);
    },
    logout: (state) => {
      state.token = null;
      state.user = null;
      state.isAuthenticated = false;
      localStorage.removeItem('token');
    },
  },
});

// לייצוא הפעולות (Actions) כדי שנוכל להפעיל אותן בקומפוננטות כמו Login
export const { setCredentials, logout } = authSlice.actions;

// יצירת ה-Store המרכזי של האפליקציה
export const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
    // בהמשך נוכל להוסיף כאן עוד reducers (למשל: parking, vehicles וכו')
  },
});

export default store;