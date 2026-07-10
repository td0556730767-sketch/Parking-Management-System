import styles from "./FloorAvailability.module.css";

function FloorRow({ level, occupied, total }) {
  const pct = Math.round((occupied / total) * 100);
  const free = total - occupied;
  const isFillingUp = pct >= 85;

  return (
    <div className={styles.row}>
      <span className={styles.floorLabel}>קומה {level}</span>

      <div
        className={styles.gauge}
        role="progressbar"
        aria-valuenow={pct}
        aria-valuemin={0}
        aria-valuemax={100}
        aria-label={`תפוסת קומה ${level}: ${occupied} מתוך ${total}`}
      >
        <div
          className={`${styles.gaugeFill} ${isFillingUp ? styles.gaugeFillHigh : ""}`}
          style={{ width: `${pct}%` }}
        />
      </div>

      <span className={styles.count}>
        <strong>{occupied}</strong> / {total}
      </span>

      <span className={styles.freeTag}>{free} פנויים</span>
    </div>
  );
}

function FloorAvailability({ floors }) {
  return (
    <div className={styles.list}>
      {floors.map((floor) => (
        <FloorRow key={floor.level} {...floor} />
      ))}
    </div>
  );
}

export default FloorAvailability;