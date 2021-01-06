package com.revature.chronicle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsConfigurationProperties corsConfigurationProperties;

    public SecurityConfig(CorsConfigurationProperties corsConfigurationProperties) {
        this.corsConfigurationProperties = corsConfigurationProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(getCorsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/*")
                        .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

    @Bean
    CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsConfigurationProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsConfigurationProperties.getAllowedMethods());
        configuration.setAllowedHeaders(corsConfigurationProperties.getAllowedHeaders());
        configuration.setExposedHeaders(corsConfigurationProperties.getExposedHeaders());
        configuration.setAllowCredentials(corsConfigurationProperties.isAllowCredentials());
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);
        return configurationSource;
    }
      
      
}