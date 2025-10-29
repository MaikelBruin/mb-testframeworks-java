package mb.testframeworks.java.integrated.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mb.demos.openapi.generated.api.client.petstore.analyzer.api.TotalsApi;
import mb.demos.openapi.generated.api.client.petstore.analyzer.model.TotalResponse;
import mb.demos.openapi.generated.api.client.petstore.api.PetApi;
import mb.demos.openapi.generated.api.client.petstore.model.Pet;
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


    public GenericStepDefs(final TestDataHolder testDataHolder, final PetApi petApi, final TotalsApi totalsApi) {
        this.testDataHolder = testDataHolder;
        this.petApi = petApi;
        this.totalsApi = totalsApi;
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

    @Given("I added a pet rat to the pet store using the petstore api")
    public void iAddedAPetRatToThePetStoreUsingThePetstoreApi() {
        log.info("i added a pet rat to the pet store using the petstore api");
    }

    @When("I check if there are any rats available using the petstore analyzer api")
    public void iCheckIfThereAreAnyRatsAvailableUsingThePetstoreAnalyzerApi() {
        log.info("iCheckIfThereAreAnyRatsAvailableUsingThePetstoreAnalyzerApi");
    }

    @Then("the petstore analyzer should return {string}")
    public void thePetstoreAnalyzerShouldReturn(String expectedString) {
        log.info("the petstore analyzer should return {}", expectedString);
    }

    @Given("I deleted all pet rats from the pet store using the petstore api")
    public void iDeletedAllPetRatsFromThePetStoreUsingThePetstoreApi() {
        log.info("I deleted all pet rats from the pet store using the petstore api");
    }
}
