package cf.scenecho.dinner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("ci")
@Configuration
public class CiProfile {
    @Bean
    String springProfile() {
        return "ci";
    }
}
