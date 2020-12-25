package com.revature.chronicle.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.revature.chronicle.security.CorsConfigurationProps;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private final CorsConfigurationProps corsConfigurationProps;
	
	

    public SecurityConfig(CorsConfigurationProps corsConfigurationProps) {
		this.corsConfigurationProps = corsConfigurationProps;
	}



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
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }
}