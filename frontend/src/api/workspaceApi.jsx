import api from "./axios";

export const getWorkspaces = () => api.get("/api/workspaces");

export const createWorkspace = (workspace) =>
  api.post("/api/workspaces", workspace);

export const deleteWorkspace = (id) => api.delete(`/api/workspaces/${id}`);

export const updateWorkspace = (id, data) =>
  api.put(`/api/workspaces/${id}`, data); 

export const getAIWorkspaceInsights = async () => {
  const response = await api.get("/api/admin/insights/workspaces");
  return response.data;
};