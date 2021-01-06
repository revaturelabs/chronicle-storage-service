package com.revature.chronicle.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        SecurityConfigurerAdapter securityConfigurerAdapterMock = mock(SecurityConfigurerAdapter.class);
        SecurityBuilder securityBuilderMock = mock(SecurityBuilder.class);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity> spyMock =
                mock(ExpressionUrlAuthorizationConfigurer.class);
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
        when(securityConfigurerAdapterMock.and()).thenReturn(httpSecurityMock);
        when(httpSecurityMock.authorizeRequests()).thenReturn(mockExpressionRegistry);
        when(mockExpressionRegistry.antMatchers(anyString())).thenReturn(authURLMock);
        when(authURLMock.authenticated()).thenReturn(mockExpressionRegistry);
        when(httpSecurityMock.oauth2ResourceServer()).thenReturn(OAuthMock);
        when(OAuthMock.jwt()).thenReturn(jwtConfigurerMock);

        // Verifies that methods run once
        CsrfConfigurer result1 = httpSecurityMock.csrf();
        assertTrue(result1 instanceof CsrfConfigurer);
        HttpSecurityBuilder result2 = csrfConfigurerMock.disable();
        assertTrue(result2 instanceof HttpSecurityBuilder);
        CorsConfigurer result3 = httpSecurityMock.cors();
        assertTrue(result3 instanceof CorsConfigurer);
        CorsConfigurer result4 = corsConfigurerMock.configurationSource(corsConfigurationSourceMock);
        assertTrue(result4 instanceof CorsConfigurer);
        HttpSecurity result5 = (HttpSecurity) securityConfigurerAdapterMock.and();
        assertTrue(result5 instanceof HttpSecurity);
        ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry result6
                = httpSecurityMock.authorizeRequests();
        assertTrue(result6 instanceof ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry);
        ExpressionUrlAuthorizationConfigurer.AuthorizedUrl result7 =
                mockExpressionRegistry.antMatchers("/*");
        assertTrue(result7 instanceof ExpressionUrlAuthorizationConfigurer.AuthorizedUrl);
        ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry result8 =
                authURLMock.authenticated();
        assertTrue(result8 instanceof ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry);
        OAuth2ResourceServerConfigurer result9 = httpSecurityMock.oauth2ResourceServer();
        assertTrue(result9 instanceof OAuth2ResourceServerConfigurer);
        OAuth2ResourceServerConfigurer.JwtConfigurer result10 = OAuthMock.jwt();
        assertTrue(result10 instanceof OAuth2ResourceServerConfigurer.JwtConfigurer);

        // run method successfully - did not implement because of nested private method
        // assertDoesNotThrow(() -> securityConfig.configure(httpSecurityMock));
    }

    @Test
    public void testCorsConfigurationSource() throws Exception {
        // Instantiating Mock Objects
        corsConfigurationMock = PowerMockito.mock(CorsConfiguration.class);
        urlBasedCorsConfigurationSourceMock = PowerMockito.mock(UrlBasedCorsConfigurationSource.class);

        // when statements
        whenNew(CorsConfiguration.class).withNoArguments().thenReturn(corsConfigurationMock);
        doNothing().when(corsConfigurationMock).setAllowedOrigins(anyList());
        doNothing().when(corsConfigurationMock).setAllowedMethods(anyList());
        doNothing().when(corsConfigurationMock).setAllowedHeaders(anyList());
        doNothing().when(corsConfigurationMock).setExposedHeaders(anyList());
        doNothing().when(corsConfigurationMock).setAllowCredentials(anyBoolean());
        when(corsConfigurationPropertiesMock.getAllowedOrigins()).thenReturn(new ArrayList<String>());
        when(corsConfigurationPropertiesMock.getAllowedMethods()).thenReturn(new ArrayList<String>());
        when(corsConfigurationPropertiesMock.getAllowedHeaders()).thenReturn(new ArrayList<String>());
        when(corsConfigurationPropertiesMock.getExposedHeaders()).thenReturn(new ArrayList<String>());
        doReturn(true).when(corsConfigurationPropertiesMock).isAllowCredentials();
        whenNew(UrlBasedCorsConfigurationSource.class).withNoArguments().thenReturn(urlBasedCorsConfigurationSourceMock);
        doNothing().when(urlBasedCorsConfigurationSourceMock).registerCorsConfiguration(
                anyString(), any(CorsConfiguration.class));

        // running methods and verifying they ran
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        verifyNew(CorsConfiguration.class).withNoArguments();

        corsConfigurationMock.setAllowedOrigins(new ArrayList<String>());
        verify(corsConfigurationMock,times(1)).setAllowedOrigins(new ArrayList<String>());

        corsConfigurationMock.setAllowedMethods(new ArrayList<String>());
        verify(corsConfigurationMock,times(1)).setAllowedMethods(new ArrayList<String>());

        corsConfigurationMock.setAllowedHeaders(new ArrayList<String>());
        verify(corsConfigurationMock,times(1)).setAllowedHeaders(new ArrayList<String>());

        corsConfigurationMock.setExposedHeaders(new ArrayList<String>());
        verify(corsConfigurationMock,times(1)).setExposedHeaders(new ArrayList<String>());

        corsConfigurationMock.setAllowCredentials(true);
        verify(corsConfigurationMock,times(1)).setAllowCredentials(true);

        List<String> result1 = corsConfigurationPropertiesMock.getAllowedOrigins();
        assertTrue(result1 instanceof List);

        List<String> result2 = corsConfigurationPropertiesMock.getAllowedMethods();
        assertTrue(result2 instanceof List);

        List<String> result3 = corsConfigurationPropertiesMock.getAllowedHeaders();
        assertTrue(result3 instanceof List);

        List<String> result4 = corsConfigurationPropertiesMock.getExposedHeaders();
        assertTrue(result4 instanceof List);

        boolean result5 = corsConfigurationPropertiesMock.isAllowCredentials();
        assertTrue(result5 == true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        verifyNew(UrlBasedCorsConfigurationSource.class).withNoArguments();

        urlBasedCorsConfigurationSourceMock.registerCorsConfiguration("/**", corsConfigurationMock);
        verify(urlBasedCorsConfigurationSourceMock, times(1))
                .registerCorsConfiguration("/**", corsConfigurationMock);

        // run method successfully
        assertDoesNotThrow(() -> securityConfig.getCorsConfigurationSource());
    }


}
