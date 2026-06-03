import api from "./axios";

export const loginUser = (data) => api.post("/api/auth/login", data);