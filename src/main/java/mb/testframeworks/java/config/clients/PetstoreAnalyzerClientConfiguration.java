package mb.testframeworks.java.config.clients;

import mb.testframeworks.java.clients.PetstoreAnalyzerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetstoreAnalyzerClientConfiguration {


    @Value("${mb-testframeworks-java.services.petstore-analyzer-v1.base-url}")
    private String baseUrl;

    @Bean
    public PetstoreAnalyzerClient petstoreAnalyzerClient() {
        return new PetstoreAnalyzerClient(baseUrl);
    }
}
