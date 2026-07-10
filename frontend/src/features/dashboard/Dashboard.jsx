import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Star, Wallet, Car, Clock } from "lucide-react";
import Sidebar from "../../components/Sidebar";
import StatCard from "./components/StatCard";
import FloorAvailability from "./components/FloorAvailability";
import QuickActions from "./components/QuickActions";
import { fetchDashboardData } from "./dashboardActions";
import styles from "./Dashboard.module.css";

const dateFormatter = new Intl.DateTimeFormat("he-IL", {
  weekday: "long",
  day: "numeric",
  month: "long",
});

function buildStatCards(data) {
  const totalFree = data.floors.reduce((sum, f) => sum + (f.total - f.occupied), 0);
  return [
    {
      key: "rewards",
      icon: Star,
      label: "נקודות תגמול",
      value: data.rewards.stars.toLocaleString("he-IL"),
      unit: "נקודות",
      accent: "amber",
      footer: `עוד ${(data.rewards.nextRewardAt - data.rewards.stars).toLocaleString("he-IL")} להטבה הבאה`,
    },
    {
      key: "wallet",
      icon: Wallet,
      label: "יתרת ארנק",
      value: `₪${data.wallet.balance.toFixed(2)}`,
      accent: "teal",
      footer: `+₪${data.wallet.addedThisWeek} נטענו השבוע`,
    },
    {
      key: "vehicles",
      icon: Car,
      label: "הרכבים שלי",
      value: data.vehicles.length,
      unit: "רכבים רשומים",
      accent: "neutral",
      footer: data.vehicles.map((v) => v.plate).join(" · "),
    },
    {
      key: "avgTime",
      icon: Clock,
      label: "זמן חניה ממוצע",
      value: data.avgParkingTimeHours,
      unit: "שעות",
      accent: "neutral",
      footer:
        data.avgParkingTimeTrendPct < 0
          ? `↓ ${Math.abs(data.avgParkingTimeTrendPct)}% פחות מהחודש שעבר`
          : `↑ ${data.avgParkingTimeTrendPct}% יותר מהחודש שעבר`,
    },
    {
      key: "freeSpots",
      icon: Car,
      label: "מקומות פנויים כרגע",
      value: totalFree,
      unit: "מתוך כלל הקומות",
      accent: "teal",
    },
  ];
}

function Dashboard() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { data, loading, error } = useSelector((state) => state.dashboard);

  useEffect(() => {
    dispatch(fetchDashboardData(328329164));
  }, [dispatch]);

  if (loading || !data) {
    return (
      <div className={styles.shell}>
        <Sidebar />
        <main className={styles.main}>
          <p className={styles.stateMessage}>טוען את לוח הבקרה…</p>
        </main>
      </div>
    );
  }

  if (error) {
    return (
      <div className={styles.shell}>
        <Sidebar />
        <main className={styles.main}>
          <p className={styles.stateMessage}>לא הצלחנו לטעון את הנתונים. נסה לרענן את הדף.</p>
        </main>
      </div>
    );
  }

  const statCards = buildStatCards(data);

  return (
    <div className={styles.shell}>
      <Sidebar />
      <main className={styles.main}>
        <header className={styles.pageHeader}>
          <div>
            <h1 className={styles.title}>ברוך שובך, {data.user.firstName}</h1>
            <p className={styles.subtitle}>
              {data.floors.reduce((s, f) => s + (f.total - f.occupied), 0)} מקומות פנויים בכל הקומות
            </p>
          </div>
          <time className={styles.date}>{dateFormatter.format(new Date())}</time>
        </header>

        <section className={styles.statsGrid} aria-label="נתוני סיכום">
          {statCards.map(({ key, ...cardProps }) => (
            <StatCard key={key} {...cardProps} />
          ))}
        </section>

        <section className={styles.panel}>
          <h2 className={styles.panelTitle}>תפוסת קומות</h2>
          <FloorAvailability floors={data.floors} />
        </section>

        <section className={styles.panel}>
          <h2 className={styles.panelTitle}>פעולות מהירות</h2>
          <QuickActions onNavigate={navigate} />
        </section>
      </main>
    </div>
  );
}

export default Dashboard;