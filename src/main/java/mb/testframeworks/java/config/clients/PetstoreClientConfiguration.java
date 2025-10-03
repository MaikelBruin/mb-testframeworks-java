package mb.testframeworks.java.config.clients;

import mb.testframeworks.java.clients.PetstoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetstoreClientConfiguration {

    @Bean
    public PetstoreClient petstoreClient() {
        return new PetstoreClient();
    }
}
