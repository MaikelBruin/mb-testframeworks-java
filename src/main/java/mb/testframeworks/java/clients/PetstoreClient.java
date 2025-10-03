package mb.testframeworks.java.clients;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
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
    private static final String BASE_URL = "https://petstore3.swagger.io/api/v3";
    private final WebTarget target;

    public PetstoreClient() {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        client.register(new JerseyRequestFilter(PetstoreClient.class));
        this.target = client.target(BASE_URL);
    }

    /**
     * Adds a new pet to the store (POST /pet).
     *
     * @param pet The pet object to add.
     * @return The Pet object returned by the server (often includes the generated ID).
     */
    public Pet addPet(Pet pet) {
        log.info("-> POST /pet: Adding pet: {}", pet.getName());
        try (Response response = target.path("pet")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pet))) {

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to add pet. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            Pet newPet = response.readEntity(Pet.class);
            log.info("<- Pet added successfully. ID: {}", newPet.getId());
            return newPet;
        }
    }

    /**
     * Finds pet by ID (GET /pet/{petId}).
     * @param petId The ID of the pet to fetch.
     * @return The Pet object.
     */
    public Pet getPetById(long petId) {
        log.info("-> GET /pet/{}: Fetching pet...", petId);
        try (Response response = target.path("pet").path(String.valueOf(petId))
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() == 404) {
                log.info("<- Pet not found (404).");
                return null;
            }

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to fetch pet. HTTP error code: "
                        + response.getStatus() + ". Body: " + response.readEntity(String.class));
            }

            Pet pet = response.readEntity(Pet.class);
            log.info("<- Pet fetched successfully: {}", pet.getName());
            return pet;
        }
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

            List<Pet> pets = response.readEntity(new GenericType<List<Pet>>() {});
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

            List<Pet> pets = response.readEntity(new GenericType<List<Pet>>() {});
            log.info("<- Found {} pets with tag '{}'.", pets.size(), tag);
            return pets;
        }
    }

    /**
     * Deletes a pet by ID (DELETE /pet/{petId}).
     * @param petId The ID of the pet to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deletePet(long petId) {
        log.info("-> DELETE /pet/{}: Deleting pet...", petId);
        try (Response response = target.path("pet").path(String.valueOf(petId))
                .request()
                .delete()) {

            if (response.getStatus() == 404) {
                log.info("<- Pet not found (404).");
                return false;
            }

            if (response.getStatus() == 200) {
                log.info("<- Pet deleted successfully.");
                return true;
            } else {
                log.info("<- Deletion failed. HTTP error code: {}", response.getStatus());
                return false;
            }
        }
    }
}