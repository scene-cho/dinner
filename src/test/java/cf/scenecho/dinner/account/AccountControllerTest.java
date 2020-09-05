package cf.scenecho.dinner.account;

import cf.scenecho.dinner.HomeController;
import cf.scenecho.dinner.exception.ExceptionAdvice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    static final String USERNAME = "scene";
    static final String EMAIL = USERNAME + "@email.com";
    static final String PASSWORD = "password!";

    @Autowired MockMvc mockMvc;
    @Autowired AccountFactory accountFactory;
    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @AfterEach
    void clearRepository() {
        accountRepository.deleteAll();
    }

    @Test
    void signupPage() throws Exception {
        mockMvc.perform(get(AccountController.SIGNUP_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(AccountController.SIGNUP_VIEW))
                .andExpect(model().attributeExists("signupForm"))
        ;
    }

    @Test
    void signup_processAndRedirect() throws Exception {
        mockMvc.perform(post(AccountController.SIGNUP_URL)
                .param("username", USERNAME)
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(AccountController.PROFILE_URL + "*"))
                .andExpect(authenticated().withUsername(USERNAME))
        ;

        Account account = accountRepository.findByUsername(USERNAME).orElse(null);
        assertThat(account).isNotNull();
        assertThat(account.getId()).isNotNull();
        assertThat(account.getUsername()).isEqualTo(USERNAME);
        assertThat(account.getEmail()).isEqualTo(EMAIL);
        assertThat(account.getPassword()).isNotEqualTo(PASSWORD);
        assertThat(passwordEncoder.matches(PASSWORD, account.getPassword())).isTrue();
    }

    @Test
    void signup_invalid_rejectAndSignupPage() throws Exception {
        mockMvc.perform(post(AccountController.SIGNUP_URL)
                .param("username", "[notAllowed]")
                .param("email", "notEmail")
                .param("password", "short")
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(view().name(AccountController.SIGNUP_VIEW))
                .andExpect(model().attributeHasFieldErrors("signupForm", "username", "email", "password"))
                .andExpect(unauthenticated())
        ;

        Account account = accountRepository.findByUsername(USERNAME).orElse(null);
        assertThat(account).isNull();
    }

    // TODO signup duplicate username

    @Test
    void login_authenticated() throws Exception {
        accountFactory.createAndSaveAccount(USERNAME);

        mockMvc.perform(formLogin()
                .user(USERNAME).password(PASSWORD)
        )
                .andExpect(authenticated().withUsername(USERNAME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
        ;
    }

    @Test
    void login_nonExistent_unauthenticated() throws Exception {
        mockMvc.perform(formLogin()
                .user(USERNAME).password(PASSWORD)
        )
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
        ;
    }

    @Test
    void login_invalidPassword_unauthenticated() throws Exception {
        accountFactory.createAndSaveAccount(USERNAME);

        mockMvc.perform(formLogin()
                .user(USERNAME).password("notThisPassword!")
        )
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
        ;
    }

    @Test
    @WithAccount(USERNAME)
    void logout_unauthenticatedAndMainPage() throws Exception {
        mockMvc.perform(logout())
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(HomeController.URL));
    }

    @Test
    @WithAccount(USERNAME)
    void profilePage_owner_detailInfo() throws Exception {
        mockMvc.perform(get(AccountController.PROFILE_URL + USERNAME))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isOwner", true))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attributeExists("password"))
                .andExpect(view().name(AccountController.PROFILE_VIEW))
        ;
    }

    @Test
    void profilePage_unauthenticatedViewer_basicInfo() throws Exception {
        accountFactory.createAndSaveAccount(USERNAME);

        mockMvc.perform(get(AccountController.PROFILE_URL + USERNAME))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isOwner", false))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attributeDoesNotExist("password"))
                .andExpect(view().name(AccountController.PROFILE_VIEW))
        ;
    }

    @Test
    @WithAccount("stranger")
    void profilePage_authenticatedStranger_basicInfo() throws Exception {
        accountFactory.createAndSaveAccount(USERNAME);

        mockMvc.perform(get(AccountController.PROFILE_URL + USERNAME))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isOwner", false))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attributeDoesNotExist("password"))
                .andExpect(view().name(AccountController.PROFILE_VIEW))
                .andExpect(authenticated().withUsername(("stranger")))
        ;
    }

    @Test
    void profilePage_nonExistent_throwAndHandleException() throws Exception {
        mockMvc.perform(get(AccountController.PROFILE_URL + USERNAME))
                .andExpect(status().isOk())
                .andExpect(view().name(ExceptionAdvice.USERNAME_NOT_FOUND))
        ;
    }

}