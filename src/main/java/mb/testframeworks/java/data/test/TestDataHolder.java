package mb.testframeworks.java.data.test;

import lombok.Getter;
import lombok.Setter;
import mb.demos.openapi.generated.api.client.petstore.analyzer.model.TotalResponse;
import mb.demos.openapi.generated.api.client.petstore.model.Pet;

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

    public TestDataHolder(String testEnv) {
        this.testEnv = testEnv;
    }
}
