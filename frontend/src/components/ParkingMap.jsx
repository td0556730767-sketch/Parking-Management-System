import React, { useEffect, useState } from 'react';
import api from '../services/api';
import Alert from './ui/Alert';
import './ParkingMap.css';

const TYPE_LABELS = {
  REGULAR: 'רגיל',
  HANDICAPPED: 'נכים',
  ELECTRIC: 'חשמלי',
  MOTORCYCLE: 'אופנוע',
  LARGE: 'גדול',
};

const ParkingMap = () => {
  const [spots, setSpots] = useState([]);
  const [floor, setFloor] = useState(null);
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    api
      .get('/api/parking/getAllParking')
      .then((res) => {
        setSpots(res.data);
        const floors = [...new Set(res.data.map((s) => s.locationX))].sort((a, b) => a - b);//מוציא מכל חניה את מספר הקומה ובונה set שמכיל את מספר הקומות שיש בלי כפילויות וממיין
        if (floors.length > 0) setFloor(floors[0]);
      })
      .catch(() => setError('לא הצלחנו לטעון את החניון'))
      .finally(() => setIsLoading(false));
  }, []);

  const floors = [...new Set(spots.map((s) => s.locationX))].sort((a, b) => a - b);
  const spotsInFloor = spots
    .filter((s) => s.locationX === floor)
    .sort((a, b) => a.locationY - b.locationY);//מוציא את כל החניות בקומה הנוכחית וממיין לפי מספר החניה

  const freeCount = spotsInFloor.filter((s) => !s.occupied).length;

  return (
    <div className="map-page">
      <h2 className="auth-title">מפת החניון</h2>

      <Alert type="error" message={error} />
      {isLoading && <p>טוען...</p>}

      <div className="floor-tabs">
        {floors.map((f) => (
          <button
            key={f}
            className={f === floor ? 'floor-tab active' : 'floor-tab'}
            onClick={() => setFloor(f)}
          >
            קומה {f}
          </button>
        ))}
      </div>

      {floor !== null && (
        <p className="map-summary">
          פנויות בקומה: <strong>{freeCount}</strong> מתוך {spotsInFloor.length}
        </p>
      )}

      <div className="spots-grid">
        {spotsInFloor.map((s) => (
          <div
            key={s.spotId}
            className={`spot ${s.occupied ? 'occupied' : 'free'} ${s.spotType}`}
          >
            <div className="spot-number">{s.locationY}</div>
            <div className="spot-type">{TYPE_LABELS[s.spotType] || s.spotType}</div>
          </div>
        ))}
      </div>

      <div className="map-legend">
        <span><i className="dot free" /> פנוי</span>
        <span><i className="dot occupied" /> תפוס</span>
      </div>
    </div>
  );
};

export default ParkingMap;