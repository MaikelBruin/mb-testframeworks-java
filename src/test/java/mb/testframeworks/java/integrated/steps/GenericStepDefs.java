package mb.testframeworks.java.integrated.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mb.testframeworks.java.clients.PetstoreAnalyzerClient;
import mb.testframeworks.java.clients.PetstoreClient;
import mb.testframeworks.java.data.test.TestDataHolder;
import mb.testframeworks.java.models.analyzer.TotalResponse;
import mb.testframeworks.java.models.petstore.Pet;
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

    @Given("I have retrieved all pets labeled {string} using the petstore api")
    public void iHaveRetrievedAllPetsLabeledUsingThePetstoreApi(String label) {
        List<Pet> response = petstoreClient.findPetsByTag(label);
        testDataHolder.setPetstoreFindByTagsResponse(response);
    }

    @When("I get all pets by tag {string} using the petstore api")
    public void iGetAllPetsByTagUsingThePetstoreApi(String tag) {
        List<Pet> response = petstoreClient.findPetsByTag(tag);
        testDataHolder.setPetstoreFindByTagsResponse(response);
    }

    @When("I get the total number of pets tagged {string} using the petstore analyzer api")
    public void iGetTheTotalNumberOfPetsTaggedUsingThePetstoreAnalyzerApi(String tag) {
        TotalResponse response = switch (tag.toUpperCase()) {
            case "DOG" -> petstoreAnalyzerClient.getTotalNumberOfDogs();
            case "CAT" -> petstoreAnalyzerClient.getTotalNumberOfCats();
            default -> throw new IllegalArgumentException("Unknown tag: " + tag);
        };
        testDataHolder.setAnalyzerGetTotalNumberOfPetsWithLabelResponse(response);
    }

    @Given("I get all pets with status {string} using the petstore api")
    public void iGetAllPetsWithStatusUsingThePetstoreApi(String status) throws InterruptedException {
        List<Pet> response = null;
        int retries = 0;
        int maxRetries = 5;
        while (retries < maxRetries && response == null) {
            log.warn("hello");
            try {
                response = petstoreClient.findPetsByStatus(status);
            } catch (Exception e) {
                log.warn("petstoreClient call failed with exception, retrying another {} times...", maxRetries - retries, e);
                Thread.sleep(5000);
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
                log.warn("petstoreAnalyzerClient call failed with exception, retrying another {} times...", maxRetries - retries, e);
                Thread.sleep(5000);
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
}
