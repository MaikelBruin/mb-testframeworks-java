package mb.testframeworks.java.config;

import io.cucumber.spring.ScenarioScope;
import org.assertj.core.api.SoftAssertions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoftAssertionConfiguration {
    @Bean
    @ScenarioScope
    public SoftAssertions softAssertions() {
        return new SoftAssertions();
    }
}
