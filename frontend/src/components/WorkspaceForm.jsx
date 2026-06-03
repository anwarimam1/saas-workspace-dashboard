import { useState } from "react";
import { toast } from "react-toastify";
import { createWorkspace } from "../api/workspaceApi";
import { useApp } from "../Context/AppContext";

function WorkspaceForm() {

  const { fetchWorkspaces, role } = useApp();

  const [workspaceName, setWorkspaceName] = useState("");

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!workspaceName.trim()) {
      toast.warning("Workspace name is required");
      return;
    }

    try {

      // ✅ Backend derives owner from JWT
      await createWorkspace({
        name: workspaceName,
      });

      toast.success("Workspace created");

      setWorkspaceName("");

      fetchWorkspaces();

    } catch (err) {

      console.error(err);

      toast.error("Error creating workspace");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">

      <h2 className="text-xl font-semibold">
        Create Workspace
      </h2>

      {/* ✅ OPTIONAL ADMIN INFO */}
      {role === "ADMIN" && (
        <p className="text-sm text-gray-500">
          Workspace will be assigned to logged-in admin.
        </p>
      )}

      <input
        className="w-full border p-2 rounded"
        placeholder="Workspace Name"
        value={workspaceName}
        onChange={(e) => setWorkspaceName(e.target.value)}
      />

      <button className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
        Create Workspace
      </button>

    </form>
  );
}

export default WorkspaceForm;