package mb.testframeworks.java.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class AllureLogbackAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent event) {
        AllureLogCollector.append(event.getFormattedMessage());
    }
}
