package com.revature.chronicle.security;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {
        "com.revature.chronicle.security.FirebaseInitializer"})
public class FirebaseInitializerTest {

    // Creating mock objects -- refactor to only instantiate when needed
    private FirebaseApp firebaseAppMock;
    private FirebaseInitializer firebaseInitializerMock;


    @Before
    public void init() {
        firebaseInitializerMock = mock(FirebaseInitializer.class);
    }

    @Test
    public void testInitializeFirebaseApp() throws Exception {
        // Initializing FirebaseApp mock
        firebaseAppMock = mock(FirebaseApp.class);
        // Making when statements for 3rd party API classes that call their own methods
        when(FirebaseInitializer.class.getResourceAsStream(anyString()))
                .thenReturn(new InputStream() {
                                @Override
                                public int read() throws IOException {
                                    return 0;
                                }
                            });
        when(GoogleCredentials.fromStream(any(InputStream.class)))
                .thenReturn(new GoogleCredentials(new AccessToken("1",
                        new Date())));
        /* Omitting FirebaseOptions from mocking because it relies on builder method,
        so the method calls are needed just to instantiate object */
        when(FirebaseApp.initializeApp(any(FirebaseOptions.class)))
                .thenReturn(FirebaseApp.getInstance());

        firebaseInitializerMock.onStart();
        verify(firebaseAppMock, times(1)).initializeApp(any(FirebaseOptions.class));
    }
}
