package mb.testframeworks.java.integrated.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mb.testframeworks.java.data.test.TestDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericStepDefs {
    public static Logger log = LoggerFactory.getLogger(GenericStepDefs.class);

    private final TestDataHolder testDataHolder;

    public GenericStepDefs(TestDataHolder testDataHolder) {
        this.testDataHolder = testDataHolder;
    }

    @Given("I have done some configuration")
    public void iHaveDoneSomeConfiguration() {
        log.info("I have done some configuration");
    }

    @When("I perform some action")
    public void iPerformSomeAction() {
        log.info("I perform some action");
    }

    @Then("I expect a result")
    public void iExpectAResult() {
        log.info("I expect a result");
    }
}
