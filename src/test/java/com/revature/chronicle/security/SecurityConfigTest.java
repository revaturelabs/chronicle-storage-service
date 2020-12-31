package com.revature.chronicle.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
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
        httpSecurityMock = mock(HttpSecurity.class);
        corsConfigurationSourceMock = mock(CorsConfigurationSource.class);

        // Method specific Mocks
        CsrfConfigurer csrfConfigurerMock = mock(CsrfConfigurer.class);
        HttpSecurityBuilder httpSecurityBuilderMock = mock(HttpSecurityBuilder.class);
        CorsConfigurer corsConfigurerMock = mock(CorsConfigurer.class);
        SecurityBuilder securityBuilderMock = mock(SecurityBuilder.class);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
        mockExpressionRegistry =
                mock(ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry.class);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authURLMock =
                mock(ExpressionUrlAuthorizationConfigurer.AuthorizedUrl.class);
        OAuth2ResourceServerConfigurer<HttpSecurity> OAuthMock =
                mock(OAuth2ResourceServerConfigurer.class);
        OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurerMock =
                mock(OAuth2ResourceServerConfigurer.JwtConfigurer.class);

                        // when statement
        when(httpSecurityMock.csrf()).thenReturn(csrfConfigurerMock);
        when(csrfConfigurerMock.disable()).thenReturn(httpSecurityMock);
        when(httpSecurityMock.cors()).thenReturn(corsConfigurerMock);
        when(corsConfigurerMock.configurationSource(any(CorsConfigurationSource.class)))
                .thenReturn(corsConfigurerMock);
        when(corsConfigurerMock.and()).thenReturn(securityBuilderMock);
        when(httpSecurityMock.authorizeRequests()).thenReturn(mockExpressionRegistry);
        when(mockExpressionRegistry.antMatchers(anyString())).thenReturn(authURLMock);
        when(authURLMock.authenticated()).thenReturn(mockExpressionRegistry);
        when(httpSecurityMock.oauth2ResourceServer()).thenReturn(OAuthMock);
        Object o = new Object();
        when(OAuthMock.jwt()).thenReturn(jwtConfigurerMock);

        // Verifies that methods run once
        httpSecurityMock.csrf();
        verify(httpSecurityMock.csrf(),times(1));
        //csrfConfigurerMock.disable();
        //verify(csrfConfigurerMock,times(1)).disable();
        httpSecurityMock.cors();
        verify(httpSecurityMock,times(1));
        corsConfigurerMock.configurationSource(corsConfigurationSourceMock);
        verify(corsConfigurerMock.configurationSource(corsConfigurationSourceMock),times(1));
        corsConfigurerMock.and();
        verify(corsConfigurerMock.and(),times(1));
        httpSecurityMock.authorizeRequests();
        verify(httpSecurityMock.authorizeRequests(),times(1));
        mockExpressionRegistry.antMatchers("/*");
        verify(mockExpressionRegistry.antMatchers("/*"),times(1));
        authURLMock.authenticated();
        verify(authURLMock.authenticated(),times(1));
        httpSecurityMock.oauth2ResourceServer();
        verify(httpSecurityMock.oauth2ResourceServer(),times(1));
        OAuthMock.jwt();
        verify(OAuthMock.jwt(),times(1));
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

        /*
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
        */

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
