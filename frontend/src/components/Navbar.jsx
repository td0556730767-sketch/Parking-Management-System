import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { logout } from '../store';
import './Navbar.css';

const Navbar = () => {
  const { isAuthenticated, user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch(logout());
    navigate('/login');
  };

  return (
    <nav className="navbar">
      {isAuthenticated ? (
        <>
          <Link to="/dashboard">האזור שלי</Link>
          <span className="navbar-user">{user?.fullName}</span>
          <button onClick={handleLogout}>התנתק</button>
        </>
      ) : (
        <Link to="/login">התחבר</Link>
      )}
    </nav>
  );
};

export default Navbar;