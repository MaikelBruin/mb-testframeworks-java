package mb.testframeworks.java.integrated.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import mb.testframeworks.java.clients.PetstoreAnalyzerClient;
import mb.testframeworks.java.clients.PetstoreClient;
import mb.testframeworks.java.data.test.TestDataHolder;
import mb.testframeworks.java.models.analyzer.HasAvailableResponse;
import mb.testframeworks.java.models.analyzer.TotalResponse;
import mb.testframeworks.java.models.petstore.Pet;
import mb.testframeworks.java.models.petstore.PetRequest;
import mb.testframeworks.java.models.petstore.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GenericStepDefs {
    public static Logger log = LoggerFactory.getLogger(GenericStepDefs.class);

    private final TestDataHolder testDataHolder;
    private final PetstoreClient petstoreClient;
    private final PetstoreAnalyzerClient petstoreAnalyzerClient;


    public GenericStepDefs(final TestDataHolder testDataHolder, final PetstoreClient petstoreClient, final PetstoreAnalyzerClient petstoreAnalyzerClient) {
        this.testDataHolder = testDataHolder;
        this.petstoreClient = petstoreClient;
        this.petstoreAnalyzerClient = petstoreAnalyzerClient;
    }

    @Given("I have done some configuration")
    public void iHaveDoneSomeConfiguration() {
        log.info("I have done some configuration");
    }

    @Then("there should be at least {int} pets with this tag in the petstore")
    public void thereShouldBeAtLeastPetsWithThisTagInThePetstore(int expectedMinimumAmountOfPets) {
        assertThat(testDataHolder.getPetstoreFindByTagsResponse().size()).isGreaterThanOrEqualTo(expectedMinimumAmountOfPets);
    }

    @When("I get all pets by tag {string} using the petstore api")
    public void iGetAllPetsByTagUsingThePetstoreApi(String tag) throws InterruptedException {
        testDataHolder.setPetstoreFindByTagsResponse(getAllPetsByTag(tag));
    }

    private List<Pet> getAllPetsByTag(String tag) throws InterruptedException {
        List<Pet> response = null;
        int retries = 0;
        int maxRetries = 5;
        while (retries < maxRetries && response == null) {
            try {
                response = petstoreClient.findPetsByTag(tag);
            } catch (Exception e) {
                log.warn("petstoreClient.findPetsByTag call failed with exception, retrying another {} times...", maxRetries - retries, e);
                Thread.sleep(2000);
                retries++;
            }
        }
        return response;
    }

    @When("I get the total number of pets tagged {string} using the petstore analyzer api")
    public void iGetTheTotalNumberOfPetsTaggedUsingThePetstoreAnalyzerApi(String tag) throws InterruptedException {
        TotalResponse response = null;
        int retries = 0;
        int maxRetries = 5;
        while (retries < maxRetries && response == null) {
            try {
                response = petstoreAnalyzerClient.findTotalNumberOfPetsByTag(tag);
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
                response = petstoreClient.findPetsByStatus(status);
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
                response = petstoreAnalyzerClient.getTotalNumberOfAvailablePets();
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
        PetRequest requestBody = new PetRequest();
        requestBody.setId(111111L);
        requestBody.setName("Rat");
        requestBody.setStatus("available");
        requestBody.setTags(List.of(new Tag(1L, "rat"), new Tag(2L, "rats")));
        log.info("-> POST pet: Adding pet: {}", new ObjectMapper().writeValueAsString(requestBody));
        petstoreClient.addPet(requestBody);
        Thread.sleep(2000);
    }

    @When("I check if there are any rats available using the petstore analyzer api")
    public void iCheckIfThereAreAnyRatsAvailableUsingThePetstoreAnalyzerApi() {
        HasAvailableResponse response = petstoreAnalyzerClient.getHasAvailableRats();
        testDataHolder.setHasAvailableRats(response);
    }

    @Then("the petstore analyzer should return {string}")
    public void thePetstoreAnalyzerShouldReturn(String expectedString) {
        boolean expected = Boolean.parseBoolean(expectedString);
        assertThat(testDataHolder.getHasAvailableRats()).isNotNull();
        assertThat(testDataHolder.getHasAvailableRats().isHasAvailable()).isEqualTo(expected);
    }

    @SneakyThrows
    @Given("I deleted all pet rats from the pet store using the petstore api")
    public void iDeletedAllPetRatsFromThePetStoreUsingThePetstoreApi() {
        List<Pet> rats = getAllPetsByTag("rat");
        rats.forEach(pet -> petstoreClient.deletePet(pet.getId()));
        rats = getAllPetsByTag("rats");
        rats.forEach(pet -> petstoreClient.deletePet(pet.getId()));
        Thread.sleep(2000);
    }

    @When("I deleted all pets from the pet store using the petstore api")
    public void iDeletedAllPetsFromThePetStoreUsingThePetstoreApi() {
        testDataHolder.getPetstoreFindByStatusResponse().forEach(pet -> petstoreClient.deletePet(pet.getId()));
    }
}
