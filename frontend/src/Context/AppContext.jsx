import { createContext, useContext, useEffect, useState } from "react";
import { getUsers } from "../api/userApi";
import { getWorkspaces } from "../api/workspaceApi";
import { toast } from "react-toastify";
import { jwtDecode } from "jwt-decode";

const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [users, setUsers] = useState([]);
  const [workspaces, setWorkspaces] = useState([]);

  const [role, setRole] = useState(null);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [usersLoading, setUsersLoading] = useState(false);
  const [workspacesLoading, setWorkspacesLoading] = useState(false);

  const [token, setToken] = useState(localStorage.getItem("token"));

  const [userEmail, setUserEmail] = useState(null);

  const fetchUsers = async () => {
    setUsersLoading(true);
    try {
      const res = await getUsers();
      console.log("USERS API:", res);
      setUsers(res.data);
      console.log("USERS RESPONSE:", res);
    } catch (err) {
      console.error("Users API ERROR:", err);
      toast.error("Failed to fetch users");
    } finally {
      setUsersLoading(false);
    }
  };

  const fetchWorkspaces = async () => {
    setWorkspacesLoading(true);
    try {
      const res = await getWorkspaces();
      console.log("WORKSPACES API:", res);
      setWorkspaces(res.data);
    } catch (err) {
      console.error("Workspace API ERROR:", err);
      toast.error("Failed to fetch workspaces");
    } finally {
      setWorkspacesLoading(false);
    }
  };

  const handleSetToken = (newToken) => {
    localStorage.setItem("token", newToken);
    setToken(newToken);

    try {
      const decoded = jwtDecode(newToken);
      setRole(decoded.role);
    } catch (err) {
      console.error("Invalid token");
      setRole(null);
    }
  };

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode(token);

        const cleanRole = decoded.role.replace("ROLE_", "");
        setRole(cleanRole);
        setUserEmail(decoded.sub); // 🔥 email from token
      } catch {
        setRole(null);
      }
    }
  }, [token]);

  useEffect(() => {
    if (token && role) {
      fetchWorkspaces();

      // ✅ ONLY ADMIN FETCHES USERS
      if (role === "ADMIN") {
        fetchUsers();
      }
    }
  }, [token, role]);

  console.log("AppContext - Role:", role);

  return (
    <AppContext.Provider
      value={{
        users,
        workspaces,
        fetchUsers,
        fetchWorkspaces,
        token,
        setToken: handleSetToken,
        role,
        loading,
        error,
        userEmail,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

// ✅ THIS LINE IS CRITICAL
export const useApp = () => useContext(AppContext);
