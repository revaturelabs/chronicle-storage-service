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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {
        "com.google.auth.oauth2.GoogleCredentials",
        "com.google.firebase.FirebaseApp",
        })
public class FirebaseInitializerTest extends FirebaseInitializer {

    // Creating mock objects -- refactor to only instantiate when needed
    private FirebaseInitializer firebaseInitializerMock;
    private FirebaseOptions firebaseOptionsMock;

    @Before
    public void init() {
        firebaseInitializerMock = mock(FirebaseInitializer.class);
    }

    @Test
    public void testInitializeFirebaseApp() throws Exception {
        // Initializing mocks
        mockStatic(FirebaseApp.class);
        mockStatic(GoogleCredentials.class);
        //firebaseOptionsMock = Mockito.mock(FirebaseOptions.class);
        InputStream streamToTest = IOUtils.toInputStream("{\"type\":\"authorized_user\"," +
                "\"client_id\":\"1\",\"client_secret\":\"1\",\"refresh_token\":\"1\"}","UTF-8");

        // When statements in mockito/powermockito
        when(firebaseInitializerMock.returnResourceAsStream(anyString()))
                .thenReturn(streamToTest);
        when(GoogleCredentials.fromStream(any(InputStream.class)))
                .thenReturn(new GoogleCredentials(new AccessToken("1",
                        new Date())));
        //when(firebaseOptionsMock.toBuilder().setCredentials(any(GoogleCredentials.class)).build())
                //.thenReturn(firebaseOptionsMock);
        FirebaseApp returnValue = FirebaseApp.getInstance();
        when(FirebaseApp.initializeApp(any(FirebaseOptions.class)))
                .thenReturn(returnValue);

        // Calling methods and verifying they've been called
        InputStream is = firebaseInitializerMock.returnResourceAsStream("/firebase-service-credentials.json");
        verify(firebaseInitializerMock,times(1));

        GoogleCredentials gc = GoogleCredentials.fromStream(streamToTest);
        assertTrue(gc instanceof GoogleCredentials);

        //FirebaseOptions options = firebaseOptionsMock.toBuilder().setCredentials(gc).build();
        //verify(firebaseOptionsMock,times(1));

        FirebaseApp fa = FirebaseApp.initializeApp(new FirebaseOptions.Builder()
                .setCredentials(gc)
                .build());
        assertTrue(fa instanceof FirebaseApp);
    }
}
