package com.revature.chronicle.security;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.revature.chronicle.controller.FileUploadController;

import lombok.extern.slf4j.Slf4j;

/**
 * service class used to initialize the connection between the server and firebase when the server starts up
 */
@Service
@Slf4j
public class FirebaseInitializer {
	private static final Logger log = LoggerFactory.getLogger(FirebaseInitializer.class);

    /**
     * runs immediately after the service is initialized. tries to call initializeFirebaseApp
     */
    @PostConstruct
    public void onStart() {
        log.info("Initializing Firebase Application");
        try {
            this.initializeFirebaseApp();
        } catch (IOException e) {
            log.error("Initializing Firebase App {}", e);
        } catch (FirebaseAuthException e) {
        	
        }
    }

    /**
     * attempts to set the connection with Firebase that will be used to authenticate the clients' JWTs. Does so using
     * the credentials in firebase-service-credentials.json
     * @throws IOException - could be thrown if google credentials cannot read the credentials file as a stream
     */
    private void initializeFirebaseApp() throws IOException, FirebaseAuthException{

        if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(
                                    //new FileInputStream(System.getenv("FIREBASE_ACCOUNT"))
                            		returnResourceAsStream("/firebase-service-credentials.json")
                            )
                    )
                    .build();

            FirebaseApp.initializeApp(options);
        }

    }

    /**
     * returns an InputStream reading the given resource
     * @param resource The resource to be read
     * @return the stream reading "resource"
     */
    protected InputStream returnResourceAsStream(String resource) {
        return FirebaseInitializer.class.getResourceAsStream(resource);
    }

}
