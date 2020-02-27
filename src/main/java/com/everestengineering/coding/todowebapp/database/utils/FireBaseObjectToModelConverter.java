/**
 * 
 */
package com.everestengineering.coding.todowebapp.database.utils;

import org.springframework.stereotype.Component;

import com.everestengineering.coding.todowebapp.model.TodoItem;
import com.google.cloud.firestore.DocumentSnapshot;

/**
 * @author Srinivas.Pakala
 *
 */

@Component
public class FireBaseObjectToModelConverter {
	
	public static TodoItem getTodoItem(DocumentSnapshot document) {
		
		return new TodoItem(document.getId(), document.getString("text"), (boolean) document.getBoolean("completed"));
	}

}
