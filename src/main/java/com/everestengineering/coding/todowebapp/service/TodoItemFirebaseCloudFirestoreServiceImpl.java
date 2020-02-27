/**
 * 
 */
package com.everestengineering.coding.todowebapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everestengineering.coding.todowebapp.database.utils.FireBaseObjectToModelConverter;
import com.everestengineering.coding.todowebapp.exceptions.NoItemFoundException;
import com.everestengineering.coding.todowebapp.model.TodoItem;
import com.everestengineering.coding.todowebapp.repository.TodoItemFirebaseCloudFirestoreRepository;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

/**
 * @author Srinivas.Pakala
 *
 */

@Service
public class TodoItemFirebaseCloudFirestoreServiceImpl implements TodoItemService {

	private static final String TODO_ITEMS = "todoItems";

	@Autowired
	private TodoItemFirebaseCloudFirestoreRepository repository;

	@Override
	public List<TodoItem> getAlltodoItems(Map<String, Object> queryParams) {

		List<QueryDocumentSnapshot> documents = new ArrayList<QueryDocumentSnapshot>();
		List<TodoItem> itemsList = new ArrayList<TodoItem>();

		try {
			documents = repository.getCollection(TODO_ITEMS, queryParams);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		documents.forEach((document) -> itemsList.add(FireBaseObjectToModelConverter.getTodoItem(document)));

		return itemsList;

	}

	@Override
	public TodoItem getTodoItem(String itemId) throws Exception {
		DocumentReference document = null;
		try {
			document = repository.getDocument(TODO_ITEMS + "/" + itemId);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		DocumentSnapshot documentSnopshot = document.get().get();

		if (documentSnopshot.exists())
			return FireBaseObjectToModelConverter.getTodoItem(documentSnopshot);

		throw new NoItemFoundException("Todo with the id=" + itemId + " is not present.");

	}

	@Override
	public boolean deleteTodoItem(String id) throws Exception {
		return repository.deleteDocument(TODO_ITEMS + "/" + id);
	}

	@Override
	public TodoItem createNewTodoItem(TodoItem item) {

		try {
			DocumentReference newDocument = repository.createDocument(TODO_ITEMS, item);
			item.setId(newDocument.getId());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public TodoItem updateTodoItem(String id, Map<String, Object> payload) throws Exception {

		DocumentReference document = null;

		try {
			document = repository.updateDocument(TODO_ITEMS + "/" + id, payload);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		if (document == null)
			throw new NoItemFoundException("Todo with the id=" + id + " is not present.");

		return FireBaseObjectToModelConverter.getTodoItem(document.get().get());

	}
}
