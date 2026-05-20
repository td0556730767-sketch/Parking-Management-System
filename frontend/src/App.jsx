import { useState } from 'react'
import { useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import Home from './Home'
import Register from './Register'
import { Route,Routes,useNavigate,useLocation } from 'react-router-dom'


function App() {

  

  //מעקף משתמשים מדומה לצורך פיתוח
  const [mockUsers, setMockUsers] = useState([
  { id: 328329164, name: "ישראל ישראלי", email: "test@test.com", license: "123456" }
]);

  // const [page, setPage] = useState("home")
  const[userContext,setuserContext] = useState(" ")
  const navigate =useNavigate()
  const location=useLocation()

  const handleNavigation=(path,data=null)=>{
    if(data)
      setuserContext(data)
    navigate(path)
  };

  const handleEnterParking = () => {
    if(!userContext.trime())
    {alert("זהו שדה חובה, אנא הכנס מספר תעודת זהות תקין")
      return;
    }
    const foundUser = mockUsers.find(user => user.id === parseInt(userContext));
    if(foundUser)
      navigate("/parking")
    else
      navigate("/register")
  };
  // בתוך קומפוננטת App:פונקציה זמנית לזמן פיתוח
  useEffect(() => {
  navigate("/");
  }, []);

  const isIsraeliIDValid = (id) => {
  id = String(id).trim();
  if (id.length > 9 || isNaN(id)) return false;
  
  // השלמה ל-9 ספרות עם אפסים מובילים במידת הצורך
  id = id.padStart(9, '0');
  
  // אלגוריתם לחישוב ספרת ביקורת
  return Array.from(id, Number).reduce((counter, digit, i) => {
    const step = digit * ((i % 2) + 1);
    return counter + (step > 9 ? step - 9 : step);
  }, 0) % 10 === 0;
};
 

  return (
    <>
      <div>
        {/* שיניתי רק את אלו כדי שה-CSS יזהה אותם */}
        <h1 className="mainTitle">Welcome to My Parking</h1>
        <h1 className="subTitle">ברוך הבא לחניה!!!🚗🚗🚗</h1>
        <h2 className="description">אנא הקש מספר תעודת זהות</h2>
        
        <Routes>
          {/* <Route path="/" element={<Home onNavigate={handleNavigation} />} /> */}
          <Route path="/register" element={<Register onNavigate={handleNavigation} />} />
          {/* <Route path="/parking" element={<Parking onNavigate={handleNavigation} />} /> */}
        </Routes>

        <input type="text" placeholder="מספר תעודת זהות" value={userContext}
        onChange={isIsraeliIDValid} required/>
        <button onClick={handleEnterParking}>כניסה לחניה</button> 

      </div>
    </>
  )
}

export default App