import { toast } from "react-toastify";
import { deleteWorkspace, updateWorkspace } from "../api/workspaceApi";
import { useApp } from "../Context/AppContext";
import { useState, useEffect } from "react";

function WorkspaceList() {

  const {
    workspaces,
    fetchWorkspaces,
    users,
    role,
    userEmail,
  } = useApp();

  // ✅ ROLE-BASED VISIBILITY
  const visibleWorkspaces =
    role === "ADMIN"
      ? workspaces
      : userEmail
        ? workspaces.filter(
            (ws) => ws.ownerEmail === userEmail
          )
        : [];

  const [selectedWorkspace, setSelectedWorkspace] =
    useState(null);

  const [formData, setFormData] = useState({
    name: "",
    userId: "",
  });

  // ✅ EDIT CLICK
  const handleEditClick = (workspace) => {
    setSelectedWorkspace(workspace);
  };

  // ✅ SYNC FORM
  useEffect(() => {

    if (selectedWorkspace) {

      setFormData({
        name: selectedWorkspace.name || "",
        userId: selectedWorkspace.ownerId || "",
      });
    }

  }, [selectedWorkspace]);

  // ✅ INPUT CHANGE
  const handleChange = (e) => {

    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // ✅ UPDATE WORKSPACE
  const handleUpdate = async (id) => {

    const payload = {
      name: formData.name,
    };

    // ✅ ONLY ADMIN CAN CHANGE OWNER
    if (role === "ADMIN" && formData.userId) {

      payload.owner = {
        id: Number(formData.userId),
      };
    }

    try {

      await updateWorkspace(id, payload);

      toast.success("Workspace updated");

      setSelectedWorkspace(null);

      setFormData({
        name: "",
        userId: "",
      });

      await fetchWorkspaces();

    } catch (err) {

      console.error("Update error:", err.response?.data);

      toast.error(
        err.response?.data?.message || "Update failed"
      );
    }
  };

  // ✅ CANCEL EDIT
  const handleCancel = () => {

    setSelectedWorkspace(null);

    setFormData({
      name: "",
      userId: "",
    });
  };

  // ✅ DELETE
  const handleDelete = async (id) => {

    try {

      await deleteWorkspace(id);

      toast.success("Workspace deleted");

      await fetchWorkspaces();

    } catch {

      toast.error("Delete failed");
    }
  };

  // ✅ LOADING
  if (!Array.isArray(workspaces)) {
    return <p>Loading workspaces...</p>;
  }

  // ✅ EMPTY STATE
  if (visibleWorkspaces.length === 0) {

    return (
      <div className="bg-white p-6 rounded-lg shadow-md text-center">

        <h2 className="text-2xl font-semibold mb-2">
          No Workspaces Found
        </h2>

        <p className="text-gray-500">
          Create your first workspace
        </p>

      </div>
    );
  }

  return (
    <div className="bg-white p-5 rounded-lg shadow-md">

      <h2 className="text-xl font-semibold mb-4">
        Workspaces
      </h2>

      {visibleWorkspaces.map((workspace) => (

        <div
          key={workspace.id}
          className="p-4 border rounded-lg mb-3 flex flex-col gap-2"
        >

          {selectedWorkspace?.id === workspace.id ? (

            <>
              {/* ✅ WORKSPACE NAME */}
              <input
                name="name"
                value={formData.name || ""}
                onChange={handleChange}
                className="border p-2 rounded"
              />

              {/* ✅ ADMIN ONLY OWNER CHANGE */}
              {role === "ADMIN" && (

                <select
                  name="userId"
                  value={formData.userId || ""}
                  onChange={handleChange}
                  className="border p-2 rounded"
                >

                  <option value="">
                    Select Owner
                  </option>

                  {(users || []).map((user) => (

                    <option
                      key={user.id}
                      value={user.id}
                    >
                      {user.name}
                    </option>

                  ))}
                </select>
              )}

              <div className="flex gap-2">

                <button
                  onClick={() =>
                    handleUpdate(workspace.id)
                  }
                  className="bg-green-500 text-white px-3 py-1 rounded"
                >
                  Save
                </button>

                <button
                  onClick={handleCancel}
                  className="bg-gray-400 text-white px-3 py-1 rounded"
                >
                  Cancel
                </button>

              </div>
            </>

          ) : (

            <div className="flex justify-between items-center">

              <div className="flex flex-col">

                <p className="font-semibold">
                  {workspace.name}
                </p>

                <p className="text-sm text-gray-500">
                  Owned by:{" "}
                  {workspace.ownerName || "No Owner"}
                </p>

              </div>

              <div className="flex gap-2">

                <button
                  onClick={() =>
                    handleEditClick(workspace)
                  }
                  className="bg-blue-500 text-white px-3 py-1 rounded"
                >
                  Edit
                </button>

                <button
                  onClick={() =>
                    handleDelete(workspace.id)
                  }
                  className="bg-red-500 text-white px-3 py-1 rounded"
                >
                  Delete
                </button>

              </div>

            </div>

          )}
        </div>
      ))}
    </div>
  );
}

export default WorkspaceList;