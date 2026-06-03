import { useNavigate } from "react-router-dom";
import { useApp } from "../Context/AppContext";

function Sidebar() {
  const navigate = useNavigate();
  const { setToken, role } = useApp();
  console.log("ROLE:", role);

  const handleLogout = () => {
    setToken(null); // this should also clear role internally
    navigate("/login");
  };

  return (
    <div className="w-64 h-screen bg-gray-800 text-white flex flex-col justify-between p-4">
      <div>
        <h2 className="text-xl font-bold mb-6">Dashboard</h2>

        <ul className="space-y-3">
          <li onClick={() => navigate("/dashboard")} className="cursor-pointer">
            Dashboard
          </li>
          
          {role === "ADMIN" && (
            <li onClick={() => navigate("/users")} className="cursor-pointer">
              Users
            </li>
          )}
          
          <li
            onClick={() => navigate("/workspaces")}
            className="cursor-pointer"
          >
            Workspaces
          </li>
        </ul>
      </div>

      {/* Logout */}
      <button
        onClick={handleLogout}
        className="bg-red-500 hover:bg-red-600 p-2 rounded"
      >
        Logout
      </button>
    </div>
  );
}

export default Sidebar;
