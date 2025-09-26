package mb.testframeworks.java.config;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebdriverConfiguration {

    @Value("${mb-testframeworks-java.is-ci}")
    private String isCi;

    @Value("${mb-testframeworks-java.webdriver.chromeVersion}")
    private String webdriverChromeVersion;

    @Bean(destroyMethod = "quit")
    @ScenarioScope
    public RemoteWebDriver webDriver() {
        boolean isCi = Boolean.parseBoolean(this.isCi);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("webSocketUrl", true);
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.setAcceptInsecureCerts(true);
        if (isCi) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.setCapability("browserVersion", webdriverChromeVersion);
            return new RemoteWebDriver(chromeOptions);
        }

        return new ChromeDriver(chromeOptions);
    }

}
