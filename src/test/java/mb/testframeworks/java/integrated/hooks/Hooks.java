package mb.testframeworks.java.integrated.hooks;

import io.cucumber.java.*;
import mb.testframeworks.java.data.test.TestDataHolder;
import mb.testframeworks.java.utils.AllureLogCollector;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Hooks {
    private static final Logger log = LoggerFactory.getLogger(Hooks.class);
    private static final String MAVEN_PROFILE = System.getProperty("maven.profile");
    private final SoftAssertions softly;
    private final TestDataHolder testDataHolder;
    private final RemoteWebDriver driver;

    @Value("${spring.profiles.active}")
    private String testEnv;

    @Value("${mb-testframeworks-java.is-ci}")
    private boolean isCi;

    public Hooks(SoftAssertions softly, TestDataHolder testDataHolder, RemoteWebDriver driver) {
        this.softly = softly;
        this.testDataHolder = testDataHolder;
        this.driver = driver;
    }

    @BeforeAll
    public static void beforeAll() {
        log.info("Running beforeAll...");
        log.info("Running with maven profile '{}'", MAVEN_PROFILE);
    }

    @Before
    public void beforeScenario() {
        log.info("Running on test environment: '{}'", testEnv);
        if (isCi) AllureLogCollector.flushToAllure();
    }

    @AfterStep
    public void afterStep() {
        softly.assertAll();
        if (isCi) AllureLogCollector.flushToAllure();
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info("Running after scenario...");
        if (isCi) AllureLogCollector.flushToAllure();
    }

    @After("@testType.ui")
    public void afterUiScenario(Scenario scenario) {
        log.info("executing after scenario block for ui tests...");
        if (scenario.isFailed()) {
            log.info("taking screenshot...");
            byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure");
            saveScreenshotToFile(screenshot, scenario.getName());
        }

        if (isCi) AllureLogCollector.flushToAllure();
    }

    public void saveScreenshotToFile(byte[] screenshotBytes, String screenshotName) {
        try {
            File screenshotDir = new File("test-output" + File.separator + "screenshots");
            if (!screenshotDir.exists()) {
                Files.createDirectories(screenshotDir.toPath());
            }

            String safeName = screenshotName.replaceAll("[^a-zA-Z0-9\\-_]", "_");
            File screenshotFile = new File(screenshotDir, System.currentTimeMillis() + "-" + safeName + ".png");
            try (FileOutputStream out = new FileOutputStream(screenshotFile)) {
                out.write(screenshotBytes);
            }

            log.debug("Screenshot saved to: {}", screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            log.info("Failed to save screenshot to: {}", e.getMessage());
        }
    }
}
