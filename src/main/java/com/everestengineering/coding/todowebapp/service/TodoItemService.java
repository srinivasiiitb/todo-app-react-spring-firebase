/**
 * 
 */
package com.everestengineering.coding.todowebapp.service;

import java.util.List;
import java.util.Map;

import com.everestengineering.coding.todowebapp.exceptions.NoItemFoundException;
import com.everestengineering.coding.todowebapp.model.TodoItem;

/**
 * @author Srinivas.Pakala
 *
 */
public interface TodoItemService {

	List<TodoItem> getAlltodoItems(Map<String, Object> queryParams);

	TodoItem getTodoItem(String itemId) throws NoItemFoundException, Exception;

	boolean deleteTodoItem(String id) throws NoItemFoundException, Exception;

	TodoItem createNewTodoItem(TodoItem newTodo);

	TodoItem updateTodoItem(String id, Map<String, Object> payload) throws NoItemFoundException, Exception;

}
