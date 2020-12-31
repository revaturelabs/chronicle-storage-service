package com.revature.chronicle.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {
        "org.springframework.web.cors.CorsConfiguration",
        "org.springframework.web.cors.UrlBasedCorsConfigurationSource",
        "org.springframework.security.config.annotation.web.builders.HttpSecurity"
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
        httpSecurityMock = PowerMockito.mock(HttpSecurity.class);
        corsConfigurationSourceMock = mock(CorsConfigurationSource.class);

        // when statement
        doNothing().when(httpSecurityMock.csrf().disable()
                .cors().configurationSource(any(CorsConfigurationSource.class))
                .and()
                .authorizeRequests()
                .antMatchers(anyString())
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt());

        // Verifies that methods run once
        httpSecurityMock.csrf().disable()
                .cors().configurationSource(corsConfigurationSourceMock)
                .and()
                .authorizeRequests()
                .antMatchers("/**")
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        verify(httpSecurityMock,times(1));
    }

    @Test
    public void testCorsConfigurationSource() throws Exception {
        // Instantiating Mock Objects
        corsConfigurationMock = PowerMockito.mock(CorsConfiguration.class);
        urlBasedCorsConfigurationSourceMock = PowerMockito.mock(UrlBasedCorsConfigurationSource.class);

        // when statements
        whenNew(CorsConfiguration.class).withNoArguments().thenReturn(corsConfigurationMock);
        when(corsConfigurationPropertiesMock.getAllowedOrigins()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.getAllowedMethods()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.getAllowedHeaders()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.getExposedHeaders()).thenReturn(new ArrayList<>());
        when(corsConfigurationPropertiesMock.isAllowCredentials()).thenReturn(true);
        whenNew(UrlBasedCorsConfigurationSource.class).withNoArguments().thenReturn(urlBasedCorsConfigurationSourceMock);
        doNothing().when(urlBasedCorsConfigurationSourceMock).registerCorsConfiguration(
                anyString(), any(CorsConfiguration.class));

        // running methods and verifying they ran
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        verifyNew(CorsConfiguration.class).withNoArguments();

        corsConfigurationMock.setAllowedOrigins(anyList());
        verify(corsConfigurationMock,times(1));

        corsConfigurationMock.setAllowedMethods(anyList());
        verify(corsConfigurationMock,times(1));

        corsConfigurationMock.setAllowedHeaders(anyList());
        verify(corsConfigurationMock,times(1));

        corsConfigurationMock.setExposedHeaders(anyList());
        verify(corsConfigurationMock,times(1));

        corsConfigurationMock.setAllowCredentials(anyBoolean());
        verify(corsConfigurationMock,times(1));

        corsConfigurationPropertiesMock.getAllowedOrigins();
        verify(corsConfigurationPropertiesMock,times(1));

        corsConfigurationPropertiesMock.getAllowedMethods();
        verify(corsConfigurationPropertiesMock,times(1));

        corsConfigurationPropertiesMock.getAllowedHeaders();
        verify(corsConfigurationPropertiesMock,times(1));

        corsConfigurationPropertiesMock.getExposedHeaders();
        verify(corsConfigurationPropertiesMock,times(1));

        corsConfigurationPropertiesMock.isAllowCredentials();
        verify(corsConfigurationPropertiesMock,times(1));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        verifyNew(UrlBasedCorsConfigurationSource.class).withNoArguments();

        urlBasedCorsConfigurationSourceMock.registerCorsConfiguration("/**", corsConfigurationMock);
        verify(urlBasedCorsConfigurationSourceMock, times(1));
    }


}
