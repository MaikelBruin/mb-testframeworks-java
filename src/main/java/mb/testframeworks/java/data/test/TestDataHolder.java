package mb.testframeworks.java.data.test;

import lombok.Getter;
import lombok.Setter;
import mb.testframeworks.java.models.petstore.Pet;

import java.util.List;

@Getter
@Setter
public class TestDataHolder {
    private String testEnv;
    private Exception apiException;
    private List<Pet> findByTagsResponse;

    public TestDataHolder(String testEnv) {
        this.testEnv = testEnv;
    }
}
