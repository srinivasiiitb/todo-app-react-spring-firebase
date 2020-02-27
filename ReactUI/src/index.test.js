import React from 'react';
import ReactDOM from 'react-dom';
import TodoItems from './TodoApp/TodoItemsApp';

it('renders without crashing', () => {
  const div = document.createElement('root');
  ReactDOM.render(<TodoItems />, div);
});