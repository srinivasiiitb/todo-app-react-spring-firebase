/**
 * 
 */
package com.everestengineering.coding.todowebapp.database.firebase;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

/**
 * @author Srinivas.Pakala
 *
 */

@Component
@Primary
public class FirebaseCloudFirestoreDatabase {

	private static Firestore FirebaseApplication = null;

	public static Firestore getInstance() {
		if (FirebaseApplication == null)
			try {
				init();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return FirebaseApplication;
	}

	private static void init() throws IOException {
		
		Resource resource = new ClassPathResource("FirebaseDBCredentials.json");
		InputStream credentialsJSON = resource.getInputStream();

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(credentialsJSON)).setProjectId("todoapp-c01ef").build();

		FirebaseApp.initializeApp(options);
		FirebaseApplication = FirestoreClient.getFirestore();
	}

}
