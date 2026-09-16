package by.filage.greetingstarter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(GreetingProperties.class)
@ConditionalOnProperty(
        prefix = "filage.greeting",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class GreetingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(GreetingService.class)
    public GreetingService greetingService() {
        return new GreetingService();
    }

    @Bean
    @ConditionalOnMissingBean(GreetingStartupRunner.class)
    public GreetingStartupRunner greetingStartupRunner(
            GreetingService greetingService,
            GreetingProperties properties) {
        return new GreetingStartupRunner(greetingService, properties);
    }
}
