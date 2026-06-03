import { useState } from "react";
import { toast } from "react-toastify";
import { createUser } from "../api/userApi";
import { useApp } from "../Context/AppContext";

function UserForm() {
  const { fetchUsers } = useApp();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);

  // Handle input change
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Submit form
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.name || !form.email || !form.password) {
      toast.warning("All fields are required");
      return;
    }

    try {
      setLoading(true);

      await createUser(form);

      toast.success("User created successfully 🚀");

      setForm({
        name: "",
        email: "",
        password: "",
      });

      fetchUsers(); // refresh list from context
    } catch (err) {
      console.error(err);
      toast.error("Error creating user");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-white p-5 rounded-lg shadow-md space-y-4"
    >
      <h2 className="text-xl font-semibold text-gray-800">
        Create User
      </h2>

      {/* Name */}
      <input
        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
        name="name"
        placeholder="Name"
        value={form.name}
        onChange={handleChange}
      />

      {/* Email */}
      <input
        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
        name="email"
        placeholder="Email"
        type="email"
        value={form.email}
        onChange={handleChange}
      />

      {/* Password */}
      <input
        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
        type="password"
        name="password"
        placeholder="Password"
        value={form.password}
        onChange={handleChange}
      />

      {/* Button */}
      <button
        type="submit"
        disabled={loading}
        className={`w-full text-white py-2 rounded transition ${
          loading
            ? "bg-gray-400 cursor-not-allowed"
            : "bg-blue-500 hover:bg-blue-600"
        }`}
      >
        {loading ? "Creating..." : "Create User"}
      </button>
    </form>
  );
}

export default UserForm;