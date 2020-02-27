import React, { useState, useEffect } from 'react';
import "./Components/TodoItems.css";
import AddTodo from './Components/AddTodo.js';
import Todo from './Components/TodoItem';
import todoItemService from './Services/TodoItemService.js';

const TodoItems = () => {

  const [allTodos, setAllTodos] = useState([]);
  const [activeTodos, setActiveTodos] = useState([]);
  const [completedTodos, setCompletedTodos] = useState([]);
  const [allTodoChecked, setAllTodoChecked] = useState();
  const [nowShowing, setNowShowing] = useState('All');
  const [showingList, setShowingList] = useState([]);

  useEffect(() => {
    todoItemService.getAllTodoItems()
      .then((response) => {
        updateItemsStats(response.data._embedded.todoItems)
      })
      .catch((err) => console.log(err));
  }, []);


  const updateItemsStats = (items) => {

    var activeTodosLocal = [];
    var completedTodosLocal = [];

    for (var i = 0; i < items.length; i++) {
      items[i].completed ? completedTodosLocal.push(items[i]) : activeTodosLocal.push(items[i]);
    }

    setAllTodos(items);
    setActiveTodos(activeTodosLocal);
    setCompletedTodos(completedTodosLocal);
    setAllTodoChecked(activeTodosLocal.length === 0 && !allTodoChecked);
    updateShowingList(items, activeTodosLocal, completedTodosLocal);
  }

  const addTodo = text => {

    var payload = { "text": text, "completed": false };

    todoItemService.createNewTodoItem(payload)
      .then((response) => {
        const tempItems = [...allTodos, response.data];
        updateItemsStats(tempItems);
      })
      .catch((err) => console.log(err));
  };


  const updateItem = (todo, payload) => {

    todoItemService.updateTodoItem(todo.id, payload)
      .then((res) => {

        const tempItems = [...allTodos];
        for (var i = 0; i < tempItems.length; i++) {
          if (todo.id === tempItems[i].id) tempItems[i] = res.data;
        }
        updateItemsStats(tempItems);
      }).catch((error) => {
        console.log("error adding item ", error);
      });
  };

  const updateShowingList = (allTodos, activeTodos, completedTodos) => {
    if (nowShowing === 'All') setShowingList(allTodos);
    else if (nowShowing === 'Active') setShowingList(activeTodos);
    else if (nowShowing === 'Completed') setShowingList(completedTodos);
  }

  const editTodo = (index, text) => {
    const tempItems = [...allTodos];
    tempItems[index].text = text;
    updateItemsStats(tempItems);
  };

  const completeAllTodo = (event, todos) => {

    const tempItems = [...allTodos];
    tempItems.map((todo) => {
      updateItem(todo, { 'completed': event.target.checked })
      todo.completed = event.target.checked;
    })
  };

  const removeTodo = index => {

    const tempItems = [...allTodos];
    todoItemService.deleteTodoItem(allTodos[index])
      .then((res) => {
        tempItems.splice(index, 1);
        updateItemsStats(tempItems);
      }).catch((error) => {
        console.log("error adding item", error);
      });
  };

  const updateTodoStatus = (currentIndex, isEdit) => {

    const newTodos = allTodos.map((todo, index) => {
      if (currentIndex === index) todo.isEdit = isEdit;
      else todo.isEdit = false;
      return todo;
    });
    setAllTodos(newTodos);
  }

  const deleteCompletedItems = () => {

    var reqArray = todoItemService.getTodoRequestArray('delete', completedTodos);
    todoItemService.deleteAllTodoItems(reqArray)
      .then((response) => {
        var tempItems = [];
        for (var i = 0; i < allTodos.length; i++) {
          if (!allTodos[i].completed) tempItems.push(allTodos[i]);
        }
        updateItemsStats(tempItems);
      }).catch((error) => {
        console.log("error adding item", error);
      });
  };

  const showItems = (items, nowShowing) => {
    setShowingList(items);
    setNowShowing(nowShowing);
  }

  return (
    <header className="todoapp">
      <h1>todos</h1>
      <div className="add-todo">
        {showingList.length !== 0 && <div ><input checked={allTodoChecked} type="checkbox" onChange={(event) => completeAllTodo(event, showingList)} /></div>}
        <div><AddTodo addTodo={addTodo} /></div>
      </div>
      {showingList.map((todo, index) => (
        <Todo
          key={index}
          index={index}
          todo={todo}
          updateTodoStatus={updateTodoStatus}
          editTodo={editTodo}
          updateItem={updateItem}
          removeTodo={removeTodo}
        />
      ))}

      {
        allTodos.length !== 0 &&
        <div className="todo">
          <div><p>{activeTodos.length} items Left</p></div>
          <div className="btn-group">
            <div className="clickable-footer"><button className={nowShowing === 'All' ? "selected" : ''} onClick={() => showItems(allTodos, 'All')} >All</button></div>
            <div className="clickable-footer"><button className={nowShowing === 'Active' ? "selected" : ''} onClick={() => showItems(activeTodos, 'Active')}>Active</button></div>
            <div className="clickable-footer"><button className={nowShowing === 'Completed' ? "selected" : ''} onClick={() => showItems(completedTodos, 'Completed')}>Completed</button></div>
          </div>
          <div>
            {
              completedTodos.length !== 0 && <div className="link"><button onClick={event => deleteCompletedItems()}>Clear Completed</button></div>
            }
          </div>
        </div>
      }
    </header>
  );
}

export default TodoItems;