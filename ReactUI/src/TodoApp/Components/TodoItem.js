import React, { useState } from 'react';
import "./TodoItems.css";
import EditTodo from './EditTodo.js';

export default function TodoItem({ todo, index, updateTodoStatus, editTodo, updateItem, removeTodo }) {
  const [isHovered, setIsHovered] = useState(false);
  const handleHover = () => setIsHovered(!isHovered);

  return (
    <div className="todo-item" onMouseEnter={handleHover} onMouseLeave={handleHover}>
      <input type="checkbox" checked={todo.completed} onChange={(event) => updateItem(todo, { 'completed': event.target.checked })} />
      {todo.isEdit ? <EditTodo index={index} todoItem={todo} editTodo={editTodo} updateItem={updateItem} updateTodoStatus={updateTodoStatus} /> :
        <div className="todo-content" style={{ textDecoration: todo.completed ? "line-through" : "" }} onDoubleClick={(event) => updateTodoStatus(index, true)}> {todo.text} </div>}
      {
        isHovered && (
          <div className="delete">
            <button onClick={() => removeTodo(index)}>x</button>
          </div>
        )
      }

    </div>
  );
}