
import React from 'react';
import "./TodoItems.css";

export default function EditTodo({ index, todoItem, editTodo, updateItem, updateTodoStatus }) {

  const handleSubmit = event => {
    event.preventDefault();
    editTodo(index, todoItem.text);
    updateItem(todoItem, { 'text': todoItem.text });
    updateTodoStatus(index, false);
  };

  return (
    <form onSubmit={handleSubmit} className="todo-content">
      <input
        type="text"
        className="todo-item"
        value={todoItem.text}
        autoFocus={true}
        onBlur={event => handleSubmit(event)}
        onChange={event => editTodo(index, event.target.value)}
      />
    </form>
  );
}