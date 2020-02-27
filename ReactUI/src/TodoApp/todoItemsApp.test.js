import React from 'react';
import ReactDOM from 'react-dom';
import TodoItemsApp from './TodoItemsApp.js';

it('renders without crashing', () => {
  ReactDOM.render(<TodoItemsApp/>, document.createElement('div'));
});