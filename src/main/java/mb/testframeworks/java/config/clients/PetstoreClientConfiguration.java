package mb.testframeworks.java.config.clients;

import mb.testframeworks.java.clients.PetstoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetstoreClientConfiguration {

    @Value("${mb-testframeworks-java.services.petstore-v3.base-url}")
    private String baseUrl;

    @Bean
    public PetstoreClient petstoreClient() {
        return new PetstoreClient(baseUrl);
    }
}
