import React from 'react';
import ReactDOM from 'react-dom';
import TodoItem from './TodoItem.js';

it('renders without crashing', () => {
  const div = document.createElement('div');

  var todoItem = {
      text: 'item',
      completed: false
  }

  ReactDOM.render(<TodoItem todo={todoItem} index={0}/>, div);
});