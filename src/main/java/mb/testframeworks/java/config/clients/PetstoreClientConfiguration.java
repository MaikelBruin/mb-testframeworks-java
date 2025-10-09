package mb.testframeworks.java.config.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import mb.demos.openapi.generated.api.client.petstore.api.PetApi;
import mb.demos.openapi.generated.api.client.petstore.api.PetApiClient;
import mb.demos.openapi.generated.api.client.petstore.client.ApiClient;
import mb.testframeworks.java.utils.JerseyRequestFilter;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetstoreClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PetstoreClientConfiguration.class);

    @Value("${mb-testframeworks-java.services.petstore-v3.base-url}")
    private String baseUrl;

    @Value("${mb-testframeworks-java.services.petstore-v3.connection-timeout}")
    private Integer connectionTimeout;

    @Bean
    public PetApi petApi(ObjectMapper objectMapper) {
        return new PetApiClient(createApiClient(objectMapper));
    }

    private ApiClient createApiClient(ObjectMapper objectMapper) {
        final ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        apiClient.setConnectTimeout(connectionTimeout);
        apiClient.setHttpClient(createJerseyClient(objectMapper));
        return apiClient;
    }

    private JerseyClient createJerseyClient(ObjectMapper objectMapper) {
        final JerseyClient client = JerseyClientBuilder.createClient();
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        jacksonJsonProvider.setMapper(objectMapper);
        client.register(jacksonJsonProvider, 1);
        client.property("jersey.config.client.connectTimeout", connectionTimeout);
        client.register(new JerseyRequestFilter(PetstoreClientConfiguration.class));
        return client;
    }
}
