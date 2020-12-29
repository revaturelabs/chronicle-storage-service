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
        /* Might need to compare to William's as this configuration is different
        then his */
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/*")
                        .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

    /* NEEDS TO BE CHANGED, FROM
   https://stackoverflow.com/questions/51719889/spring-boot-cors-issue
    */

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsConfigurationProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsConfigurationProperties.getAllowedMethods());
        configuration.setAllowedHeaders(corsConfigurationProperties.getAllowedHeaders());
        configuration.setExposedHeaders(corsConfigurationProperties.getExposedHeaders());
        configuration.setAllowCredentials(corsConfigurationProperties.isAllowCredentials());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
      
      
      
      /*
 

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.cors(spec -> {
	            CorsConfiguration config = new CorsConfiguration();
        		config.setAllowCredentials(true);
	            config.addAllowedOriginPattern("*");
	            config.addAllowedHeader("*");
	    		config.addAllowedMethod("*");
	            

//	            config.setAllowedMethods(corsConfigurationProps.getAllowedMethods());
//	            config.setAllowedHeaders(corsConfigurationProps.getAllowedHeaders());
//	            config.setExposedHeaders(corsConfigurationProps.getExposedHeaders());
//	            config.setAllowCredentials(corsConfigurationProps.isAllowCredentials());
	            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	            source.registerCorsConfiguration("/**", config);
	            spec.configurationSource(source);
        		})
        		.csrf().disable()

                .authorizeRequests()
                .antMatchers("/*")
                        .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

      
      
      
      
      
      
      */
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
}