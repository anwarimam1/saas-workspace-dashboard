import { Navigate } from "react-router-dom";
import { useApp } from "../Context/AppContext";

const PrivateRoute = ({ children, allowedRoles }) => {
  const { token, role } = useApp();

  if (!token) {
    return <Navigate to="/login" />;
  }

  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/dashboard" />;
  }

  return children;
};

export default PrivateRoute;