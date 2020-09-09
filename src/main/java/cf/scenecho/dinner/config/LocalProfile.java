package cf.scenecho.dinner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Configuration
public class LocalProfile {
    @Bean
    String springProfile() {
        return "local";
    }
}
