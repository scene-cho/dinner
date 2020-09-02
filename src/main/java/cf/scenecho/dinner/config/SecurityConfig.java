package cf.scenecho.dinner.config;

import cf.scenecho.dinner.HomeController;
import cf.scenecho.dinner.account.controller.ProfileController;
import cf.scenecho.dinner.account.controller.SignUpController;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String wildcard = "*";
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,
                        HomeController.URL, SignUpController.URL,
                        ProfileController.BASE_URL + wildcard)
                .permitAll()

                .mvcMatchers(HttpMethod.POST,
                        SignUpController.URL)
                .permitAll()

                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}