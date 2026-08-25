import { useState, useEffect } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

import api from "../api/axios";
import { useApp } from "../Context/AppContext";

function Login() {

  const navigate = useNavigate();

  const { token, setToken } = useApp();

  // ✅ Redirect if already logged in
  useEffect(() => {

    if (token) {
      navigate("/dashboard");
    }

  }, [token, navigate]);

  // ✅ Form state
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  // ✅ Input change
  const handleChange = (e) => {

    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  // ✅ Login
  const handleLogin = async (e) => {

    e.preventDefault();

    try {

      const res = await api.post("/auth/login", {
        email: form.email,
        password: form.password,
      });

      console.log("LOGIN RESPONSE:", res);

      // ✅ Axios interceptor already unwraps response
      // Backend returns:
      // {
      //   statusCode,
      //   message,
      //   data: TOKEN
      // }

      const token = res.token;

      if (!token) {
        toast.error("Token not received");
        return;
      }

      // ✅ Save token globally
      setToken(token);

      toast.success("Login successful");

      navigate("/dashboard");

    } catch (err) {

      console.error("LOGIN ERROR:", err);

      toast.error(
        err.response?.data?.message || "Login failed"
      );
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">

      <form
        onSubmit={handleLogin}
        className="bg-white p-6 rounded-lg shadow-md w-80 space-y-4"
      >

        <h2 className="text-xl font-semibold text-center">
          Login
        </h2>

        {/* ✅ EMAIL */}
        <input
          name="email"
          type="email"
          placeholder="Email"
          className="w-full border p-2 rounded"
          value={form.email}
          onChange={handleChange}
        />

        {/* ✅ PASSWORD */}
        <input
          name="password"
          type="password"
          placeholder="Password"
          autoComplete="current-password"
          className="w-full border p-2 rounded"
          value={form.password}
          onChange={handleChange}
        />

        {/* ✅ SUBMIT */}
        <button
          type="submit"
          className="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded transition duration-300"
        >
          Login
        </button>

      </form>
    </div>
  );
}

export default Login;