function EmptyState({ message = "No data available" }) {
  return (
    <div className="flex flex-col items-center justify-center h-[60vh]">
      <p className="text-lg font-semibold">{message}</p>
      <p className="text-gray-500">
        Start by creating users or workspaces
      </p>
    </div>
  );
}

export default EmptyState;