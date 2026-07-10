export const mockDashboardData = {
  user: {
    id: 328329164,
    firstName: "אלכס",
  },
  wallet: {
    balance: 245.5,
    addedThisWeek: 15,
  },
  rewards: {
    stars: 1250,
    nextRewardAt: 2000,
  },
  vehicles: [
    { plate: "12-345-67", type: "regular" },
    { plate: "98-765-43", type: "disabled" },
  ],
  avgParkingTimeHours: 2.5,
  avgParkingTimeTrendPct: -12,
  floors: [
    { level: 1, occupied: 76, total: 200 },
    { level: 2, occupied: 80, total: 200 },
    { level: 3, occupied: 75, total: 200 },
    { level: 4, occupied: 62, total: 200 },
    { level: 5, occupied: 74, total: 200 },
  ],
}