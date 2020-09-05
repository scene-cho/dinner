package cf.scenecho.dinner.config;

import cf.scenecho.dinner.HomeController;
import cf.scenecho.dinner.account.AccountController;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,
                        HomeController.URL, AccountController.SIGNUP_URL, AccountController.PROFILE_URL + "*")
                .permitAll()

                .mvcMatchers(HttpMethod.POST,
                        AccountController.SIGNUP_URL)
                .permitAll()

                .anyRequest()
                .authenticated();

        http.formLogin()
                .loginPage(AccountController.LOGIN_URL).permitAll()
                .successHandler(authenticationSuccessHandler());
        http.logout()
                .logoutSuccessUrl(HomeController.URL);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
                HttpSession session = request.getSession();
                String prevPage = (String) session.getAttribute("prev");
                if (prevPage != null) {
                    session.removeAttribute("prev");
                    getRedirectStrategy().sendRedirect(request, response, prevPage);
                } else {
                    super.onAuthenticationSuccess(request, response, authentication);
                }
            }
        };
        successHandler.setDefaultTargetUrl(HomeController.URL);
        return successHandler;
    }

}
