package mb.testframeworks.java.data.test;

import lombok.Getter;
import lombok.Setter;
import mb.testframeworks.java.models.analyzer.HasAvailableResponse;
import mb.testframeworks.java.models.analyzer.TotalResponse;
import mb.testframeworks.java.models.petstore.Pet;

import java.util.List;

@Getter
@Setter
public class TestDataHolder {
    private String testEnv;
    private Exception apiException;
    private List<Pet> petstoreFindByTagsResponse;
    private List<Pet> petstoreFindByStatusResponse;
    private TotalResponse analyzerGetTotalNumberOfPetsWithLabelResponse;
    private TotalResponse analyzerGetTotalNumberOfAvailablePetsResponse;
    private HasAvailableResponse hasAvailableRats;

    public TestDataHolder(String testEnv) {
        this.testEnv = testEnv;
    }
}
