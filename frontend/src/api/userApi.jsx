import api from "./axios";

// GET users
export const getUsers = () => api.get("/api/users");

// CREATE user
export const createUser = (user) => api.post("/api/users", user);

// DELETE user
export const deleteUser = (id) => api.delete(`/api/users/${id}`);

//UPDATE USER
export const updateUser = (id, data) =>
  api.put(`/api/users/${id}`, data);