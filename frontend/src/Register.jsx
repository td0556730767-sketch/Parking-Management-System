import styles from './Register.module.css';
import { useEffect } from 'react';
function Register(props) {
    useEffect(() => {
        // This code will run when the component is mounted
        props.onNavigate("register");  
    }, []);
  return (
    <div className={styles.registerContainer} >
        
      <h1 className={styles.mainTitle}>אינך  רשום לחניה </h1>
      <form className={styles.registerForm}>
        <label htmlFor="name">שם מלא:</label>
        <input type="text" id="name" name="name" required />  
        <label htmlFor="email">דוא"ל:</label>
        <input type="email" id="email" name="email" required />
        <label htmlFor="license">מספר רישוי:</label>
        <input type="text" id="license" name="license" required />
        <button type="submit">הרשם</button>
      </form>    

      {/* Registration form goes here */}
    </div>
  );
}
export default Register