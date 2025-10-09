package mb.testframeworks.java.config.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import mb.demos.openapi.generated.api.client.petstore.analyzer.api.TotalsApi;
import mb.demos.openapi.generated.api.client.petstore.analyzer.api.TotalsApiClient;
import mb.demos.openapi.generated.api.client.petstore.analyzer.client.ApiClient;
import mb.testframeworks.java.utils.JerseyRequestFilter;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetstoreAnalyzerClientConfiguration {

    @Value("${mb-testframeworks-java.services.petstore-analyzer-v1.base-url}")
    private String baseUrl;

    @Value("${mb-testframeworks-java.services.petstore-analyzer-v1.connection-timeout}")
    private Integer connectionTimeout;

    @Bean
    public TotalsApi totalsApi(ObjectMapper objectMapper) {
        return new TotalsApiClient(createApiClient(objectMapper));
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
        client.register(new JerseyRequestFilter(PetstoreAnalyzerClientConfiguration.class));
        return client;
    }
}
