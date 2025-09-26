package mb.testframeworks.java.integrated.config;

import io.cucumber.spring.CucumberContextConfiguration;
import mb.testframeworks.java.TestConfig;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {TestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CucumberSpringConfiguration {
}
