package cf.scenecho.dinner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Configuration
    @Profile("local")
    static class LocalProfile {
        @Bean
        String springProfile() {
            return "local";
        }
    }

    @Configuration
    @Profile("dev")
    static class DevProfile {
        @Bean
        String springProfile() {
            return "dev";
        }
    }

    @Configuration
    @Profile("prod")
    static class ProdProfile {
        @Bean
        String springProfile() {
            return "prod";
        }
    }

    @Configuration
    @Profile("test")
    static class TestProfile {
        @Bean
        String springProfile() {
            return "test";
        }
    }
}
