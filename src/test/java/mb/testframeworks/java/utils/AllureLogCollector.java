package mb.testframeworks.java.utils;

import io.qameta.allure.Allure;

public class AllureLogCollector {
    private static final ThreadLocal<StringBuilder> LOG_BUFFER = ThreadLocal.withInitial(StringBuilder::new);

    public static void append(String message) {
        LOG_BUFFER.get().append(message).append(System.lineSeparator());
    }

    public static void flushToAllure() {
        String logs = LOG_BUFFER.get().toString();
        if (!logs.isEmpty()) {
            Allure.addAttachment("Test logs", "text/plain", logs);
        }
        LOG_BUFFER.remove(); // clean up
    }
}