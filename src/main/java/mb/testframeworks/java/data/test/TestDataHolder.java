package mb.testframeworks.java.data.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDataHolder {
    //overall
    private String testEnv;
    private Exception apiException;

    public TestDataHolder(String testEnv) {
        this.testEnv = testEnv;
    }
}
