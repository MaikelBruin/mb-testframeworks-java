package mb.testframeworks.java.integrated.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import mb.demos.openapi.generated.api.client.petstore.analyzer.api.AvailableApi;
import mb.demos.openapi.generated.api.client.petstore.analyzer.api.TotalsApi;
import mb.demos.openapi.generated.api.client.petstore.analyzer.model.HasAvailableResponse;
import mb.demos.openapi.generated.api.client.petstore.analyzer.model.TotalResponse;
import mb.demos.openapi.generated.api.client.petstore.api.PetApi;
import mb.demos.openapi.generated.api.client.petstore.client.ApiException;
import mb.demos.openapi.generated.api.client.petstore.model.Pet;
import mb.demos.openapi.generated.api.client.petstore.model.Tag;
import mb.testframeworks.java.data.test.TestDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GenericStepDefs {
    public static Logger log = LoggerFactory.getLogger(GenericStepDefs.class);

    private final TestDataHolder testDataHolder;
    private final PetApi petApi;
    private final TotalsApi totalsApi;
    private final AvailableApi availableApi;
    private final ObjectMapper objectMapper;


    public GenericStepDefs(final TestDataHolder testDataHolder, final PetApi petApi, final TotalsApi totalsApi, final AvailableApi availableApi, final ObjectMapper objectMapper) {
        this.testDataHolder = testDataHolder;
        this.petApi = petApi;
        this.totalsApi = totalsApi;
        this.availableApi = availableApi;
        this.objectMapper = objectMapper;
    }

    @Given("I have done some configuration")
    public void iHaveDoneSomeConfiguration() {
        log.info("I have done some configuration");
    }

    @When("I get all pets by tag {string} using the petstore api")
    public void iGetAllPetsByTagUsingThePetstoreApi(String tag) throws InterruptedException {
        List<Pet> response = null;
        int retries = 0;
        int maxRetries = 5;
        while (retries < maxRetries && response == null) {
            try {
                response = petApi.findPetsByTags(List.of(tag));
            } catch (Exception e) {
                log.warn("petstoreClient.findPetsByTag call failed with exception, retrying another {} times...", maxRetries - retries, e);
                Thread.sleep(2000);
                retries++;
            }
        }
        testDataHolder.setPetstoreFindByTagsResponse(response);
    }

    @When("I get the total number of pets tagged {string} using the petstore analyzer api")
    public void iGetTheTotalNumberOfPetsTaggedUsingThePetstoreAnalyzerApi(String tag) throws InterruptedException {
        TotalResponse response = null;
        int retries = 0;
        int maxRetries = 5;
        while (retries < maxRetries && response == null) {
            try {
                response = totalsApi.findTotalNumberOfPetsByTag(tag);
            } catch (Exception e) {
                log.warn("petstoreAnalyzerClient.findTotalNumberOfPetsByTag call failed with exception, retrying another {} times...", maxRetries - retries, e);
                Thread.sleep(2000);
                retries++;
            }
        }

        testDataHolder.setAnalyzerGetTotalNumberOfPetsWithLabelResponse(response);
    }

    @Given("I get all pets with status {string} using the petstore api")
    public void iGetAllPetsWithStatusUsingThePetstoreApi(String status) throws InterruptedException {
        List<Pet> response = null;
        int retries = 0;
        int maxRetries = 5;
        while (retries < maxRetries && response == null) {
            try {
                response = petApi.findPetsByStatus(status);
            } catch (Exception e) {
                log.warn("petstoreClient.findPetsByStatus call failed with exception, retrying another {} times...", maxRetries - retries, e);
                Thread.sleep(2000);
                retries++;
            }
        }

        testDataHolder.setPetstoreFindByStatusResponse(response);
    }

    @When("I get the total number of available pets using the petstore analyzer api")
    public void iGetTheTotalNumberOfAvailablePetsUsingThePetstoreAnalyzerApi() throws InterruptedException {
        TotalResponse response = null;
        int retries = 0;
        int maxRetries = 5;
        while (retries < maxRetries && response == null) {
            try {
                response = totalsApi.getTotalNumberOfAvailablePets();
            } catch (Exception e) {
                log.warn("petstoreAnalyzerClient.getTotalNumberOfAvailablePets call failed with exception, retrying another {} times...", maxRetries - retries, e);
                Thread.sleep(2000);
                retries++;
            }
        }

        testDataHolder.setAnalyzerGetTotalNumberOfAvailablePetsResponse(response);
    }

    @Then("the total number of available pets from the analyzer api should be equal to the total from the petstore api")
    public void theTotalNumberOfAvailablePetsFromTheAnalyzerApiShouldBeEqualToTheTotalFromThePetstoreApi() {
        int expected = testDataHolder.getPetstoreFindByStatusResponse().size();
        Integer actual = testDataHolder.getAnalyzerGetTotalNumberOfAvailablePetsResponse().getTotal();
        assertThat(actual).isEqualTo(expected);
    }

    @Then("the total number of tagged pets from the analyzer api should be equal to the total of the petstore api")
    public void theTotalNumberOfTaggedPetsFromTheAnalyzerApiShouldBeEqualToTheTotalOfThePetstoreApi() {
        int expected = testDataHolder.getPetstoreFindByTagsResponse().size();
        Integer actual = testDataHolder.getAnalyzerGetTotalNumberOfPetsWithLabelResponse().getTotal();
        assertThat(actual).isEqualTo(expected);
    }

    @Then("the petstore get pets by tag response should not be null")
    public void thePetstoreGetPetsByTagResponseShouldNotBeNull() {
        assertThat(testDataHolder.getPetstoreFindByTagsResponse()).isNotNull();
    }

    @SneakyThrows
    @Given("I added a pet rat to the pet store using the petstore api")
    public void iAddedAPetRatToThePetStoreUsingThePetstoreApi() {
        Pet pet = new Pet();
        pet.setId(121111L);
        pet.setName("Ratjetoe");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        pet.setTags(List.of(new Tag().id(1L).name("rat"), new Tag().id(2L).name("rats")));
        log.info("adding pet with body '{}'", objectMapper.writeValueAsString(pet));
        petApi.addPet(pet);
        Thread.sleep(2000);
    }

    @SneakyThrows
    @When("I check if there are any rats available using the petstore analyzer api")
    public void iCheckIfThereAreAnyRatsAvailableUsingThePetstoreAnalyzerApi() {
        HasAvailableResponse response = availableApi.getHasAvailableRats();
        testDataHolder.setHasAvailableRatsResponse(response);
    }

    @Then("the petstore analyzer should return {string}")
    public void thePetstoreAnalyzerShouldReturn(String expectedString) {
        boolean expected = Boolean.parseBoolean(expectedString);
        assertThat(testDataHolder.getHasAvailableRatsResponse()).isNotNull();
        assertThat(testDataHolder.getHasAvailableRatsResponse().getHasAvailable()).isEqualTo(expected);
    }

    @SneakyThrows
    @Given("I deleted all pet rats from the pet store using the petstore api")
    public void iDeletedAllPetRatsFromThePetStoreUsingThePetstoreApi() {
        List<Pet> rats = petApi.findPetsByTags(List.of("rat", "rats"));
        rats
            .stream()
            .filter(pet -> pet.getId() != null)
            .forEach(pet -> {
                try {
                    petApi.deletePet(pet.getId(), null);
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            });
        Thread.sleep(2000);
    }
}
