package mb.testframeworks.java.config.data;

import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import mb.testframeworks.java.data.test.TestDataHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class TestDataHolderConfiguration {

    @Value("${spring.profiles.active}")
    private String testEnv;

    @ScenarioScope
    @Bean
    public TestDataHolder testDataHolder() {
        return new TestDataHolder(testEnv);
    }
}
