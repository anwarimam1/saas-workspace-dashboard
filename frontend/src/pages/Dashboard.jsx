import { useApp } from "../Context/AppContext";
import AdminDashboard from "./AdminDashboard";
import UserDashboard from "./UserDashboard";

function Dashboard() {
  const { role } = useApp();

  if (role === "ADMIN") {
    return <AdminDashboard />;
  }

  return <UserDashboard />;
}

export default Dashboard;