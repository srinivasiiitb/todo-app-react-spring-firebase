import React, { useState } from 'react';
import "./TodoItems.css";

export default function AddTodo({ addTodo }) {
  const [value, setValue] = useState("");

  const handleSubmit = e => {
    e.preventDefault();
    if (!value) return;
    addTodo(value);
    setValue("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="What needs to be done?"
        value={value}
        autoFocus={true}
        onChange={e => setValue(e.target.value)}
      />
    </form>
  );
}
