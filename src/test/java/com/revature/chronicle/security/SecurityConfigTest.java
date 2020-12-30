package com.revature.chronicle.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {
        "org.springframework.security.config.annotation.web.builders.HttpSecurity",
        "com.revature.chronicle.security.SecurityConfig",
        "org.springframework.web.cors.CorsConfiguration",
        "org.springframework.web.cors.CorsConfigurationSource",
        "org.springframework.web.cors.UrlBasedCorsConfigurationSource"
})
public class SecurityConfigTest {

    // Creating mock objects -- refactor to only instantiate when needed
    private HttpSecurity httpSecurityMock;
    private SecurityConfig securityConfig;
    private CorsConfiguration corsConfigurationMock;
    private CorsConfigurationSource corsConfigurationSourceMock;
    private CorsConfigurationProperties corsConfigurationPropertiesMock;
    private UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSourceMock;


    @Before
    public void init() {
        corsConfigurationPropertiesMock = mock(CorsConfigurationProperties.class);
        securityConfig = new SecurityConfig(corsConfigurationPropertiesMock);
    }


    @Test
    public void testConfigureMethod() throws Exception {
        // Instantiating Mock Objects
        httpSecurityMock = mock(HttpSecurity.class);
        corsConfigurationSourceMock = mock(CorsConfigurationSource.class);

        // return a mock CorsConfigurationSource when method called
        when(securityConfig.corsConfigurationSource())
                .thenReturn(corsConfigurationSourceMock);

        // Verifies that methods was run once
        securityConfig.configure(httpSecurityMock);
        verify(securityConfig, times(1)).configure(httpSecurityMock);
    }

    @Test
    public void testCorsConfigurationSource() {
        // Instantiating Mock Objects
        corsConfigurationMock = mock(CorsConfiguration.class);
        urlBasedCorsConfigurationSourceMock = mock(UrlBasedCorsConfigurationSource.class);

        // return empty list or boolean when corsConfigurationPropertiesMock methods called
        when(corsConfigurationPropertiesMock.getAllowedOrigins()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.getAllowedMethods()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.getAllowedHeaders()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.getExposedHeaders()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.isAllowCredentials()).thenReturn(true);

        // do nothing when registerCorsConfiguration is called
        doNothing().when(urlBasedCorsConfigurationSourceMock).registerCorsConfiguration(
                anyString(), any(CorsConfiguration.class));

        // run corsConfigurationSource() method
        CorsConfigurationSource result = securityConfig.corsConfigurationSource();

        // verify that registerCorsConfiguration was called and method returns correct object
        verify(urlBasedCorsConfigurationSourceMock, times(1)).
                registerCorsConfiguration(anyString(), any(CorsConfiguration.class));
        assertTrue(result instanceof CorsConfigurationSource && result != null);
    }


}
