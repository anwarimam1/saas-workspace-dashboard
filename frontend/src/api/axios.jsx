import axios from "axios";

const api = axios.create({
  baseURL: "/api",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  console.log("TOKEN BEING SENT:", token);

  // 🔥 Skip auth header for login
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

// RESPONSE INTERCEPTOR
api.interceptors.response.use(
  (response) => response.data,
  (error) => {
    const status = error.response?.status;

    if (status === 401) {
      console.log("AUTH ERROR:", status);

      // remove token
      localStorage.removeItem("token");

      // redirect
      window.location.href = "/login";
    }

    return Promise.reject(error);
  },
);

export default api;
