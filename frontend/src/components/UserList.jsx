import { toast } from "react-toastify";
import { deleteUser, updateUser } from "../api/userApi";
import { useApp } from "../Context/AppContext";
import { useState, useEffect } from "react";

function UserList() {
  const { users, fetchUsers } = useApp();

  if (!users) return <p>Loading users...</p>;

  const [selectedUser, setSelectedUser] = useState(null);

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
  });

  // ✅ Edit click
  const handleEditClick = (user) => {
    setSelectedUser(user);
  };

  // ✅ Sync form
  useEffect(() => {
    if (selectedUser) {
      setFormData({
        name: selectedUser.name || "",
        email: selectedUser.email || "",
        password: "",
      });
    }
  }, [selectedUser]);

  // ✅ Input change
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // ✅ UPDATE (PUT-compatible full payload)
  const handleUpdate = async (id) => {
    const payload = {
      name: formData.name || selectedUser.name,
      email: formData.email || selectedUser.email,
      password: formData.password ? formData.password : "",
    };

    console.log("PUT Payload:", payload);

    try {
      await updateUser(id, payload);

      toast.success("User updated");

      setSelectedUser(null);
      setFormData({
        name: "",
        email: "",
        password: "",
      });

      await fetchUsers();
    } catch (err) {
      console.error("STATUS:", err.response?.status);
      console.error("DATA:", err.response?.data);

      toast.error(err.response?.data?.message || "Update failed");
    }
  };

  // ✅ Cancel edit
  const handleCancel = () => {
    setSelectedUser(null);
    setFormData({
      name: "",
      email: "",
      password: "",
    });
  };

  // ✅ Delete user
  const handleDelete = async (id) => {
    try {
      await deleteUser(id);
      toast.success("User deleted");
      await fetchUsers();
    } catch (err) {
      console.error("DELETE STATUS:", err.response?.status);
      console.error("DELETE DATA:", err.response?.data);

      toast.error(err.response?.data?.message || "Delete failed");
    }
  };

  return (
    <div className="bg-white p-5 rounded-lg shadow-md">
      <h2 className="text-xl font-semibold mb-4">Users</h2>

      {users.map((user) => (
        <div
          key={user.id}
          className="p-4 border rounded-lg mb-3 flex flex-col gap-2"
        >
          {selectedUser?.id === user.id ? (
            <>
              <input
                name="name"
                value={formData.name || ""}
                onChange={handleChange}
                className="border p-2 rounded"
              />

              <input
                name="email"
                value={formData.email || ""}
                onChange={handleChange}
                className="border p-2 rounded"
              />

              <input
                name="password"
                type="password"
                value={formData.password || ""}
                onChange={handleChange}
                placeholder="Enter new password (optional)"
                className="border p-2 rounded"
              />

              <div className="flex gap-2">
                <button
                  onClick={() => handleUpdate(user.id)}
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
              <div>
                <p className="font-semibold">{user.name}</p>
                <p className="text-sm text-gray-500">{user.email}</p>
              </div>

              <div className="flex gap-2">
                <button
                  onClick={() => handleEditClick(user)}
                  className="bg-blue-500 text-white px-3 py-1 rounded"
                >
                  Edit
                </button>

                <button
                  onClick={() => handleDelete(user.id)}
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

export default UserList;
