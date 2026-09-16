package by.filage.greetingstarter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "filage.greeting")
public class GreetingProperties {
    private boolean enabled = true;
    private String message = "Привет!";
    private String currency = "BYN";
    private String dateFormat = "dd.MM.yyyy HH:mm:ss";
}
