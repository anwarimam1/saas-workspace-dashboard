import { useApp } from "../Context/AppContext";
import Loader from "../components/Loader";
import ErrorState from "../components/ErrorState";
import EmptyState from "../components/EmptyState";
import { useState } from "react";
import { Cell } from "recharts";
import { getAIWorkspaceInsights } from "../api/workspaceApi";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

const colors = ["#8884d8", "#82ca9d", "#ffc658", "#ff7f7f", "#8dd1e1"];

function AdminDashboard() {
  const { users, workspaces, loading, error } = useApp();

  const [aiInsights, setAiInsights] = useState("");
  const [aiLoading, setAiLoading] = useState(false);
  const [aiError, setAiError] = useState("");

  const fetchAIInsights = async () => {
    try {
      setAiLoading(true);
      setAiError("");

      const response = await getAIWorkspaceInsights();

      setAiInsights(
        response?.data?.insights ||
          response?.insights ||
          "No insights available.",
      );
    } catch (err) {
      console.error("AI ERROR:", err);
      console.error("RESPONSE:", err.response);
      console.error("DATA:", err.response?.data);

      setAiError("Failed to load AI insights.");
    } finally {
      setAiLoading(false);
    }
  };

  const totalUsers = users?.length || 0;
  const totalWorkspaces = workspaces?.length || 0;
  const workspaceCountMap = {};

  (workspaces || []).forEach((ws) => {
    const ownerEmail = ws.ownerEmail;

    if (ownerEmail) {
      workspaceCountMap[ownerEmail] = (workspaceCountMap[ownerEmail] || 0) + 1;
    }
  });

  const chartData =
    users?.map((user) => ({
      name: user.name,
      workspaces: workspaceCountMap[user.email] || 0,
    })) || [];

  if (loading) return <Loader />;

  if (error) return <ErrorState message={error} />;

  if (!users || !workspaces) {
    return <Loader />;
  }

  return (
    <div>
      <h1 className=" space-y-6 pb-10 text-2xl font-bold mb-6">Dashboard</h1>

      {/* Metrics */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition">
          <h2 className="text-gray-500">Total Users</h2>
          <p className="text-3xl font-bold mt-2">{totalUsers}</p>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition">
          <h2 className="text-gray-500">Total Workspaces</h2>
          <p className="text-3xl font-bold mt-2">{totalWorkspaces}</p>
        </div>
      </div>

      {/* AI Insights */}
      <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition overflow-hidden">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">AI Workspace Insights</h2>

          <button
            onClick={fetchAIInsights}
            disabled={aiLoading}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {aiInsights ? "Refresh Insights" : "Generate Insights"}
          </button>
        </div>

        {aiLoading && (
          <div className="animate-pulse space-y-4">
            <div className="h-4 bg-gray-300 rounded w-3/4"></div>
            <div className="h-4 bg-gray-300 rounded w-full"></div>
            <div className="h-4 bg-gray-300 rounded w-5/6"></div>
            <div className="h-4 bg-gray-300 rounded w-2/3"></div>
          </div>
        )}

        {aiError && <p className="text-red-500">{aiError}</p>}

        {!aiLoading && !aiError && aiInsights && (
          <div className="bg-gray-50 p-4 rounded whitespace-pre-line text-gray-700 leading-relaxed">
            {aiInsights}
          </div>
        )}
      </div>

      {/* 📊 Chart */}
      <div className="bg-white p-3 rounded-lg shadow-md hover:shadow-lg transition duration-300">
        <h2 className="text-lg font-semibold mb-4">Workspaces per User</h2>

        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={chartData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="workspaces">
              {chartData.map((entry, index) => (
                <Cell
                  key={`cell-${index}`}
                  fill={colors[index % colors.length]}
                />
              ))}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}

export default AdminDashboard;
