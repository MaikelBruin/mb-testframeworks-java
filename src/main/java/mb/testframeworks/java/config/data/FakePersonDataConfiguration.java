package mb.testframeworks.java.config.data;

import mb.testframeworks.java.data.fake.FakePersonData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakePersonDataConfiguration {

    @Value("${spring.profiles.active}")
    private String testEnv;

    @Bean
    public FakePersonData fakePersonData() {
        return new FakePersonData(testEnv);
    }
}
