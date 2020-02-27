import React from 'react';
import ReactDOM from 'react-dom';
import AddTodo from './AddTodo';

it('renders without crashing', () => {
  const div = document.createElement('root');

  var todoItem = {
      text: 'item',
      completed: false
  }

  ReactDOM.render(<AddTodo addTodo={todoItem}/>, div);
});