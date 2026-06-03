function ErrorState({ message }) {
  return (
    <div className="flex flex-col items-center justify-center h-[60vh]">
      <p className="text-red-500 text-lg font-semibold mb-2">
        Something went wrong
      </p>
      <p className="text-gray-500">{message}</p>
    </div>
  );
}

export default ErrorState;