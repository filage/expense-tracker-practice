package by.filage.greetingstarter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingStartupRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(GreetingStartupRunner.class);

    private final GreetingService greetingService;
    private final GreetingProperties properties;

    public GreetingStartupRunner(
            GreetingService greetingService,
            GreetingProperties properties) {
        this.greetingService = greetingService;
        this.properties = properties;
    }

    @Override
    public void run(ApplicationArguments args) {
        String formattedDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(properties.getDateFormat()));

        String startupMessage = greetingService.createStartupMessage(
                properties.getMessage(),
                formattedDate,
                properties.getCurrency()
        );

        log.info("{}", startupMessage);
    }
}
