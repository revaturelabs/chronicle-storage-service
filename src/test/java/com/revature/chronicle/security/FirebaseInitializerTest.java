package com.revature.chronicle.security;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {
        "com.google.auth.oauth2.GoogleCredentials",
        "com.google.firebase.FirebaseApp",
        })
public class FirebaseInitializerTest extends FirebaseInitializer {

    // Creating mock objects -- refactor to only instantiate when needed
    private FirebaseInitializer firebaseInitializer;
    private FirebaseInitializer firebaseInitializerMock;
    private FirebaseOptions.Builder firebaseBuilderOptionsMock;
    private FirebaseOptions firebaseOptionsMock;
    private FirebaseApp fireBaseAppMock;

    @Before
    public void init() {
        firebaseInitializerMock = mock(FirebaseInitializer.class);
        firebaseInitializer = new FirebaseInitializer();
    }

    @Test
    public void testInitializeFirebaseApp() throws Exception {
        // Initializing mocks
        mockStatic(FirebaseApp.class);
        mockStatic(GoogleCredentials.class);
        fireBaseAppMock = mock(FirebaseApp.class);
        firebaseBuilderOptionsMock = Mockito.mock(FirebaseOptions.Builder.class);
        firebaseOptionsMock = mock(FirebaseOptions.class);
        InputStream streamToTest = IOUtils.toInputStream("{\"type\":\"authorized_user\"," +
                "\"client_id\":\"1\",\"client_secret\":\"1\",\"refresh_token\":\"1\"}","UTF-8");

        // When statements in mockito/powermockito
        when(firebaseInitializerMock.returnResourceAsStream(anyString()))
                .thenReturn(streamToTest);
        when(GoogleCredentials.fromStream(any(InputStream.class)))
                .thenReturn(new GoogleCredentials(new AccessToken("1",
                        new Date())));
        when(firebaseBuilderOptionsMock.build())
                .thenReturn(firebaseOptionsMock);
        when(FirebaseApp.initializeApp(any(FirebaseOptions.class)))
                .thenReturn(fireBaseAppMock);

        // Calling methods and verifying they've been called
        InputStream is = firebaseInitializerMock.returnResourceAsStream("");
        verify(firebaseInitializerMock,times(1)).returnResourceAsStream("");

        GoogleCredentials gc = GoogleCredentials.fromStream(streamToTest);
        assertTrue(gc != null);

        FirebaseOptions options = firebaseBuilderOptionsMock.build();
        verify(firebaseBuilderOptionsMock,times(1)).build();

        FirebaseApp fa = FirebaseApp.initializeApp(new FirebaseOptions.Builder()
                .setCredentials(gc)
                .build());
        assertTrue(fa != null);

        // run method successfully
        assertDoesNotThrow(() -> firebaseInitializer.onStart());
    }
}
