import React from 'react';
import ReactDOM from 'react-dom';
import EditTodo from './EditTodo.js';

it('renders without crashing', () => {
  const div = document.createElement('div');

  var todoItem = {
      text: 'item',
      completed: false
  }

  ReactDOM.render(<EditTodo index={0} todoItem={todoItem}/>, div);
});