/**  
 * @Title: ConfigurationVertX.java
 * @Package: com.moma.vertxboot.beans
 * @author: Ivan
 * @date: Jun 12, 2017 2:51:42 PM
 * @version: V1.0  
 */

package com.moma.vertxboot.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.vertx.core.Vertx;

/**
 * <p>Company: itic</p>
 * 
 * @author: Ivan
 * @date: Jun 12, 2017 2:51:42 PM
 * @version: V1.0
 */
@Configuration
public class ConfigurationVertX {

    private Vertx vertx;

    /**
     * A singleton instance of {@link Vertx} which is used throughout the
     * application
     * 
     * @return An instance of {@link Vertx}
     */
    @Bean
    public Vertx getVertxInstance() {
        if (this.vertx == null) {
            this.vertx = Vertx.vertx();
        }
        return this.vertx;
    }

    /**
     * Create an {@link ObjectMapper} for use in (de)serializing objects to/from
     * JSON
     * 
     * @return An instance of {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper(new JsonFactory());
    
        
}
