package com.revature.chronicle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * configuration for Spring Security.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * the bean used which will hold all of the CORS configurateion properties
     */
    private final CorsConfigurationProperties corsConfigurationProperties;

    /**
     * setter for corsConfigurationProperties
     * @param corsConfigurationProperties - the new CorsConfigurationProperties to be set
     */
    public SecurityConfig(CorsConfigurationProperties corsConfigurationProperties) {
        this.corsConfigurationProperties = corsConfigurationProperties;
    }

    /**
     * configures spring security
     * @param http the HttpSecurity object to be configured
     * @throws Exception  indicates something has gone wrong
     */
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

    /**
     * creates a CorsConfigurationSource to be used in the configure method to set up our CORS permissions. Uses the
     * corsConfigurationProperties object to accomplish this.
     * @return - the CorsConfigurationSource object used to set Spring Security's CORS permissions
     */
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