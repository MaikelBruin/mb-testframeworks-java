package mb.testframeworks.java.clients;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import mb.testframeworks.java.models.petstore.Pet;
import mb.testframeworks.java.utils.JerseyRequestFilter;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

/**
 * A client wrapper for the Petstore API using a Jersey 3 (JAX-RS) Client.
 */
@Slf4j
public class PetstoreClient {
    private final WebTarget target;

    public PetstoreClient(String baseUrl) {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        client.register(new JerseyRequestFilter(PetstoreClient.class));
        this.target = client.target(baseUrl);
    }

    /**
     * Finds pets by status (GET /pet/findByStatus?status={status}).
     * @param status The status to filter by (e.g., "available").
     * @return A list of Pet objects.
     */
    public List<Pet> findPetsByStatus(String status) {
        log.info("-> GET /pet/findByStatus?status={}: Finding pets...", status);
        try (Response response = target.path("pet/findByStatus")
                .queryParam("status", status)
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to find pets by status. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            List<Pet> pets = response.readEntity(new GenericType<>() {
            });
            log.info("<- Found {} pets with status '{}'.", pets.size(), status);
            return pets;
        }
    }

    /**
     * TODO: should support providing multiple tags
     * @param tag
     * @return
     */
    public List<Pet> findPetsByTag(String tag) {
        log.info("-> GET /pet/findByTags?tag={}: Finding pets...", tag);
        try (Response response = target.path("pet/findByTags")
                .queryParam("tags", tag)
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to find pets by tag. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            List<Pet> pets = response.readEntity(new GenericType<>() {
            });
            log.info("<- Found {} pets with tag '{}'.", pets.size(), tag);
            return pets;
        }
    }

}