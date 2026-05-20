import React from 'react';
import styles from './Home.module.css'
import { useEffect } from 'react';
function Home(props) {
  useEffect(() => {
    // This code will run when the component is mounted
    props.onNavigate("home");
  }, []);
  return (
    <div className={styles.homeContainer}>
      <h1 className={styles.mainTitle}>Welcome to My Parking</h1>
      <h1 className={styles.subTitle}>ברוך הבא לחניה!!!🚗🚗🚗</h1>
      
    </div>
  );
}
export default Home