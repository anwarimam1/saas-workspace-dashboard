import { useApp } from "../Context/AppContext";

function UserDashboard() {

  const {
    workspaces,
    userEmail,
    loading,
    error,
  } = useApp();

  if (loading) {
    return (
      <p className="text-center mt-10">
        Loading...
      </p>
    );
  }

  if (error) {
    return (
      <p className="text-center mt-10 text-red-500">
        {error}
      </p>
    );
  }

  // ✅ FIXED FILTER
  const myWorkspaces = userEmail
    ? workspaces.filter(
        (ws) => ws.ownerEmail === userEmail
      )
    : [];

  // ✅ EMPTY STATE
  if (myWorkspaces.length === 0) {

    return (
      <div className="text-center mt-10">

        <p className="text-lg font-semibold">
          No Workspaces Found
        </p>

        <p className="text-gray-500">
          Create your first workspace
        </p>

      </div>
    );
  }

  return (
    <div>

      <h1 className="text-2xl font-bold mb-6">
        My Dashboard
      </h1>

      {/* ✅ WORKSPACE COUNT */}
      <div className="bg-white p-6 rounded-lg shadow-md">

        <h2 className="text-gray-500">
          My Workspaces
        </h2>

        <p className="text-3xl font-bold mt-2">
          {myWorkspaces.length}
        </p>

      </div>

      {/* ✅ WORKSPACE LIST */}
      <div className="bg-white p-6 rounded-lg shadow-md mt-6">

        <h2 className="text-xl font-semibold mb-4">
          My Workspace List
        </h2>

        <div className="space-y-3">

          {myWorkspaces.map((workspace) => (

            <div
              key={workspace.id}
              className="border rounded-lg p-4"
            >

              <p className="font-semibold">
                {workspace.name}
              </p>

              <p className="text-sm text-gray-500">
                Owner: {workspace.ownerName}
              </p>

            </div>
          ))}

        </div>
      </div>
    </div>
  );
}

export default UserDashboard;