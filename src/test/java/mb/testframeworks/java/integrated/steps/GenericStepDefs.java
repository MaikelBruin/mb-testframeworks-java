package mb.testframeworks.java.integrated.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mb.testframeworks.java.clients.PetstoreClient;
import mb.testframeworks.java.data.test.TestDataHolder;
import mb.testframeworks.java.models.petstore.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GenericStepDefs {
    public static Logger log = LoggerFactory.getLogger(GenericStepDefs.class);

    private final TestDataHolder testDataHolder;
    private final PetstoreClient petstoreClient;


    public GenericStepDefs(final TestDataHolder testDataHolder, final PetstoreClient petstoreClient) {
        this.testDataHolder = testDataHolder;
        this.petstoreClient = petstoreClient;
    }

    @Given("I have done some configuration")
    public void iHaveDoneSomeConfiguration() {
        log.info("I have done some configuration");
    }

    @When("I get all pets by tag {string}")
    public void iGetAllPetsByTag(String tag) {
        List<Pet> response = petstoreClient.findPetsByTag(tag);
        testDataHolder.setFindByTagsResponse(response);
    }

    @Then("there should be at least {int} pets with this tag in the petstore")
    public void thereShouldBeAtLeastPetsWithThisTagInThePetstore(int expectedMinimumAmountOfPets) {
        assertThat(testDataHolder.getFindByTagsResponse().size()).isGreaterThanOrEqualTo(expectedMinimumAmountOfPets);
    }
}
