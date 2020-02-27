/**
 * 
 */
package com.everestengineering.coding.todowebapp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.everestengineering.coding.todowebapp.database.firebase.FirebaseCloudFirestoreDatabase;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

/**
 * @author Srinivas.Pakala
 *
 */

@Repository
public class TodoItemFirebaseCloudFirestoreRepository {

	private Firestore databaseInstance = FirebaseCloudFirestoreDatabase.getInstance();

	public List<QueryDocumentSnapshot> getCollection(String string, Map<String, Object> queryParams)
			throws InterruptedException, ExecutionException {

		CollectionReference todoItemCollection = databaseInstance.collection(string);

		ApiFuture<QuerySnapshot> quarySnapshotList = null;

		if (queryParams != null && !queryParams.isEmpty()) {
			Query query = buildQueryFromParams(todoItemCollection, queryParams);
			quarySnapshotList = query.get();
		} else {
			quarySnapshotList = todoItemCollection.get();
		}

		QuerySnapshot querySnapshot = quarySnapshotList.get();
		return querySnapshot.getDocuments();
	}

	private Query buildQueryFromParams(CollectionReference todoItemCollection, Map<String, Object> queryParams) {

		Query query = null;
		for (Entry<String, Object> entry : queryParams.entrySet()) {
			if (!entry.getKey().equals("orderBy")) {
				Object value = entry.getValue();
				if (value != null && (value.equals("false") || value.equals("true"))) {
					query = todoItemCollection.whereEqualTo(entry.getKey(), Boolean.valueOf((String) value));
				}
			}
		}

		if (queryParams.containsKey("orderBy")) {
			query.orderBy((String) queryParams.get("orderBy"));
		}
		return query;
	}


	private DocumentSnapshot getDocumentSnapshot(String path) throws Exception {

		DocumentSnapshot document = databaseInstance.document(path).get().get();

		if (!document.exists())
			throw new Exception("No Document found for=" + path);

		return document;
	}
	
	public DocumentReference getDocument(String path) throws Exception {

		DocumentSnapshot document = databaseInstance.document(path).get().get();

		if (!document.exists())
			throw new Exception("No Document found for=" + path);

		return document.getReference();
	}

	public DocumentReference createDocument(String collectionName,Object object) throws InterruptedException, ExecutionException {

		DocumentReference document = databaseInstance.collection(collectionName).document();

		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", document.getId());

		ApiFuture<WriteResult> result = document.set(object);
		fields.put("createdOn", result.get().getUpdateTime());
		document.update(fields);

		return document;
	}

	public DocumentReference updateDocument(String id, Map<String, Object> fields) throws Exception {

		if (id == null)
			throw new Exception("Document id can not be null");

		DocumentSnapshot documentSnapshot = this.getDocumentSnapshot(id);

		if (!documentSnapshot.exists())
			throw new Exception("No document found with id=" + id);

		DocumentReference document = documentSnapshot.getReference();
		document.update(fields);
		return getDocument(id);
	}

	public boolean deleteDocument(String id) throws Exception {

		if (id == null)
			throw new Exception("Document id can not be null");

		DocumentSnapshot documentSnapshot = this.getDocumentSnapshot(id);

		if (!documentSnapshot.exists())
			throw new Exception("No document found with id=" + id);

		DocumentReference document = documentSnapshot.getReference();
		document.delete();
		return false;
	}

}
