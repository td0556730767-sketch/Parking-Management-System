import { NavLink } from "react-router-dom";
import {
  LayoutGrid,
  Map,
  Car,
  Search,
  Settings,
} from "lucide-react";
import styles from "./Sidebar.module.css";

const NAV_ITEMS = [
  { to: "/dashboard", label: "לוח בקרה", icon: LayoutGrid, end: true },
  { to: "/parking-map", label: "מפת חניה", icon: Map },
  { to: "/my-garage", label: "הרכבים שלי", icon: Car },
  { to: "/find-car", label: "איפה הרכב שלי", icon: Search },
  { to: "/admin", label: "פאנל ניהול", icon: Settings },
];

function Sidebar() {
  return (
    <aside className={styles.sidebar}>
      <div className={styles.brand}>
        <span className={styles.brandMark} aria-hidden="true">P</span>
        <div>
          <div className={styles.brandName}>SmartPark</div>
          <div className={styles.brandTagline}>חניה חכמה</div>
        </div>
      </div>

      <nav className={styles.nav} aria-label="ניווט ראשי">
        {NAV_ITEMS.map(({ to, label, icon: Icon, end }) => (
          <NavLink
            key={to}
            to={to}
            end={end}
            className={({ isActive }) =>
              isActive ? `${styles.navItem} ${styles.navItemActive}` : styles.navItem
            }
          >
            <Icon size={18} strokeWidth={2} />
            <span>{label}</span>
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}

export default Sidebar; 