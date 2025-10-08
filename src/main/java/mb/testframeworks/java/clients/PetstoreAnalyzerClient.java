package mb.testframeworks.java.clients;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import mb.testframeworks.java.models.analyzer.AvailabilityRatioResponse;
import mb.testframeworks.java.models.analyzer.TotalResponse;
import mb.testframeworks.java.utils.JerseyRequestFilter;
import org.glassfish.jersey.client.ClientConfig;

@Slf4j
public class PetstoreAnalyzerClient {
    private final WebTarget target;

    public PetstoreAnalyzerClient(String baseUrl) {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        client.register(new JerseyRequestFilter(PetstoreAnalyzerClient.class));
        this.target = client.target(baseUrl);
    }

    // --- Ratios Endpoints ---

    /**
     * Gets the ratio of available pets vs sold pets (GET /api/ratios/availablity).
     *
     * @return The AvailabilityRatioResponse object.
     */
    public AvailabilityRatioResponse getPetAvailabilityRatio() {
        log.info("-> GET /api/ratios/availablity: Getting pet availability ratio...");
        final String path = "api/ratios/availablity";

        try (Response response = target.path(path)
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to get pet availability ratio. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            AvailabilityRatioResponse ratioResponse = response.readEntity(AvailabilityRatioResponse.class);
            log.info("<- Pet availability ratio fetched: {} (Available: {}, Sold: {})",
                    ratioResponse.getRatio(), ratioResponse.getAvailablePets(), ratioResponse.getSoldPets());
            return ratioResponse;
        }
    }

    // --- Totals Endpoints ---

    /**
     * Gets the total number of dogs in the petstore (GET /api/totals/dogs).
     *
     * @return The TotalResponse object.
     */
    public TotalResponse getTotalNumberOfDogs() {
        log.info("-> GET /api/totals/dogs: Getting total number of dogs...");
        final String path = "api/totals/dogs";

        try (Response response = target.path(path)
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to get total number of dogs. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            TotalResponse totalResponse = response.readEntity(TotalResponse.class);
            log.info("<- Total number of dogs fetched: {}", totalResponse.getTotal());
            return totalResponse;
        }
    }

    /**
     * Gets the total number of cats in the petstore (GET /api/totals/cats).
     *
     * @return The TotalResponse object.
     */
    public TotalResponse getTotalNumberOfCats() {
        log.info("-> GET /api/totals/cats: Getting total number of cats...");
        final String path = "api/totals/cats";

        try (Response response = target.path(path)
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to get total number of cats. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            TotalResponse totalResponse = response.readEntity(TotalResponse.class);
            log.info("<- Total number of cats fetched: {}", totalResponse.getTotal());
            return totalResponse;
        }
    }

    /**
     * Gets the total number of available pets in the petstore (GET /api/totals/available).
     * @return The TotalResponse object.
     */
    public TotalResponse getTotalNumberOfAvailablePets() {
        log.info("-> GET /api/totals/available: Getting total number of available pets...");
        final String path = "api/totals/available";

        try (Response response = target.path(path)
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to get total number of available pets. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            TotalResponse totalResponse = response.readEntity(TotalResponse.class);
            log.info("<- Total number of available pets fetched: {}", totalResponse.getTotal());
            return totalResponse;
        }
    }
}