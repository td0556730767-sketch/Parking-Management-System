import { useState, useEffect } from 'react'
import { Route, Routes, useNavigate } from 'react-router-dom'
import './App.css'
import Register from './Register'
import Dashboard from './features/dashboard/Dashboard' 

function App() {

  // מאגר משתמשים מדומה לצורך פיתוח
  const [mockUsers, setMockUsers] = useState([
    { id: "328329164", name: "ישראל ישראלי", email: "test@test.com", license: "123456" }
  ]);

  const [idInput, setIdInput] = useState(""); // שומר את מה שהמשתמש מקליד
  const [gateState, setGateState] = useState("closed"); // מצבי המחסום: closed, checking, open
  const [showGuestOptions, setShowGuestOptions] = useState(false); // האם להציג אפשרויות לאורח
  
  const navigate = useNavigate();

  // אלגוריתם תעודת זהות (נשאר כפי שכתבת, רק החזרנו אותו לשימוש תקין)
  const isIsraeliIDValid = (id) => {
    id = String(id).trim();
    if (id.length > 9 || isNaN(id)) return false;
    id = id.padStart(9, '0');
    return Array.from(id, Number).reduce((counter, digit, i) => {
      const step = digit * ((i % 2) + 1);
      return counter + (step > 9 ? step - 9 : step);
    }, 0) % 10 === 0;
  };

  const handleEnterParking = () => {
    const cleanId = idInput.trim();

    if (!cleanId) {
      alert("זהו שדה חובה, אנא הכנס מספר תעודת זהות תקין");
      return;
    }

    // שלב 1: שינוי מצב המחסום ל"בדיקה" כדי ליצור אפקט של מערכת חכמה שעובדת
    setGateState("checking");

    // סימולציה של חצי שנייה בדיקה בשרת
    setTimeout(() => {
      const foundUser = mockUsers.find(user => user.id === cleanId);

      if (foundUser) {
        // שלב 2: אם נמצא מנוי - המחסום נפתח!
        setGateState("open");
        
        // מחכים שנייה וחצי שהאנימציה של המחסום תסתיים ואז עוברים לדשבורד
        setTimeout(() => {
          navigate("/parking", { state: { user: foundUser, type: "member" } });
          resetForm();
        }, 1500);
      } else {
        // שלב 3: המשתמש לא מנוע - המחסום נשאר סגור ומציגים לו אפשרויות
        setGateState("closed");
        setShowGuestOptions(true);
      }
    }, 1200);
  };

  // פונקציה שמטפלת בבחירה של משתמש שאינו מנוי
  const handleGuestChoice = (choiceType) => {
    if (choiceType === 'register') {
      // מעבר לעמוד הרשמה כמנוי
      navigate("/register");
    } else {
      // המשך כחניה חד פעמית (מעבירים את המידע על המחיר היקר יותר ב-state של הראוטר)
      navigate("/parking", { state: { type: "guest", price: 40 } });
    }
    resetForm();
  };

  const resetForm = () => {
    setIdInput("");
    setGateState("closed");
    setShowGuestOptions(false);
  };

  return (
  <Routes>
    <Route path="/register" element={<Register />} />
    <Route path="/parking" element={<Dashboard/>} />
    <Route path="/" element={
      <div className="app-container">
        <h1 className="mainTitle">Welcome to My Parking</h1>
        <h1 className="subTitle">ברוך הבא לחניה!!! 🚗🚗🚗</h1>

        <div className="parking-gate-simulation">
          <div className="barrier-post">
            <div className={`barrier-arm ${gateState}`}></div>
          </div>
          <div className="car-wrapper">🚗</div>
        </div>

        {!showGuestOptions ? (
          <div className="form-zone">
            <h2 className="description">אנא הקש מספר תעודת זהות</h2>
            <input 
              type="text" 
              placeholder="מספר תעודת זהות" 
              value={idInput}
              onChange={(e) => setIdInput(e.target.value)}
              disabled={gateState === "checking"} 
              required
            />
            <button onClick={handleEnterParking} disabled={gateState === "checking"}>
              {gateState === "checking" ? "בודק במאגר..." : "כניסה לחניה"}
            </button> 
          </div>
        ) : (
          <div className="guest-options">
            <h3>לא נמצא מנוי פעיל עבור תעודת זהות זו</h3>
            <p>החניה כרוכה בתשלום. בחר כיצד להמשיך:</p>
            <div className="options-buttons">
              <button className="btn-member" onClick={() => handleGuestChoice('register')}>
                להצטרפות כמנוי וקבלת הטבות ✨
              </button>
              <button className="btn-one-time" onClick={() => handleGuestChoice('guest')}>
                חניה חד-פעמית (תעריף מלא: 40 ₪) 💰
              </button>
            </div>
          </div>
        )}
      </div>
    } />
  </Routes>
)
}

export default App