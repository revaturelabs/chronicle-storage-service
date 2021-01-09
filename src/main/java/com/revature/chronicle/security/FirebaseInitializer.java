package com.revature.chronicle.security;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
@Slf4j
public class FirebaseInitializer {

    @PostConstruct
    public void onStart() {
        log.info("Initializing Firebase Application");
        try {
            this.initializeFirebaseApp();
        } catch (IOException e) {
            log.error("Initializing Firebase App {}", e);
        }
    }

    private void initializeFirebaseApp() throws IOException {

        if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(
                                    returnResourceAsStream("/firebase-service-credentials.json")
                            )
                    )
                    .build();

            FirebaseApp.initializeApp(options);
        }

    }

    protected InputStream returnResourceAsStream(String resource) {
        return FirebaseInitializer.class.getResourceAsStream(resource);
    }

}