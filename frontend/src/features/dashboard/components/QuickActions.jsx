import { Navigation, CarFront, CreditCard, Star } from "lucide-react";
import styles from "./QuickActions.module.css";

function buildActions(onNavigate) {
  return [
    {
      key: "wiz",
      label: "נווט אותי לחניה פנויה",
      icon: Navigation,
      accent: "amber",
      onClick: () => onNavigate("/parking-map"),
    },
    {
      key: "simulate",
      label: "הדמיית כניסת רכב",
      icon: CarFront,
      accent: "teal",
      onClick: () => onNavigate("/simulate/entry"),
    },
    {
      key: "payments",
      label: "תשלומים ועסקאות",
      icon: CreditCard,
      accent: "neutral",
      onClick: () => onNavigate("/payments"),
    },
    {
      key: "rewards",
      label: "ההטבות שלי",
      icon: Star,
      accent: "neutral",
      onClick: () => onNavigate("/rewards"),
    },
  ];
}

function QuickActions({ onNavigate }) {
  const actions = buildActions(onNavigate);

  return (
    <div className={styles.grid}>
      {actions.map(({ key, label, icon: Icon, accent, onClick }) => (
        <button
          key={key}
          type="button"
          className={`${styles.action} ${styles[`accent-${accent}`]}`}
          onClick={onClick}
        >
          <span className={styles.iconWrap}>
            <Icon size={20} strokeWidth={2} />
          </span>
          <span className={styles.label}>{label}</span>
        </button>
      ))}
    </div>
  );
}

export default QuickActions;