package com.revature.chronicle.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * holds the set of permissions for cross-origin requests to the service. pulls these lists from application.properties
 */
@Component
@ConfigurationProperties(prefix = "cors")
@Data
public class CorsConfigurationProperties {

    /**
     * list of origins which are allowed to send requests to the server
     */
    private List<String> allowedOrigins;
    /**
     * list of HTTP methods that can be used to query the server
     */
    private List<String> allowedMethods;
    /**
     * list of the headers the client is allowed to set on their HTTP requests to the server
     */
    private List<String> allowedHeaders;
    /**
     * list of response headers that the client is allowed to read
     */
    private List<String> exposedHeaders;
    /**
     * tells browsers whether to expose the response to frontend JavaScript code when the request's credentials mode
     * (Request.credentials) is "include".
     */
    private boolean allowCredentials;

}
