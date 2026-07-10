import styles from "./StatCard.module.css";

function StatCard({ icon: Icon, label, value, unit, accent = "neutral", footer }) {
  return (
    <div className={`${styles.card} ${styles[`accent-${accent}`]}`}>
      <div className={styles.header}>
        <span className={styles.label}>{label}</span>
        {Icon && (
          <span className={styles.iconWrap}>
            <Icon size={18} strokeWidth={2} />
          </span>
        )}
      </div>

      <div className={styles.valueRow}>
        <span className={styles.value}>{value}</span>
        {unit && <span className={styles.unit}>{unit}</span>}
      </div>

      {footer && <div className={styles.footer}>{footer}</div>}
    </div>
  );
}

export default StatCard;