import api from "./axios";

export const getWorkspaces = () => api.get("/workspaces");

export const createWorkspace = (workspace) =>
  api.post("/workspaces", workspace);

export const deleteWorkspace = (id) => api.delete(`/workspaces/${id}`);

export const updateWorkspace = (id, data) =>
  api.put(`/workspaces/${id}`, data); 

export const getAIWorkspaceInsights = async () => {
  const response = await api.get("/admin/insights/workspaces");
  return response.data;
};