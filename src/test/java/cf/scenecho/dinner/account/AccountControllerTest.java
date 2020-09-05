package cf.scenecho.dinner.account;

import cf.scenecho.dinner.exception.ExceptionAdvice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Autowired MockMvc mockMvc;
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
                .param("username", TestAccount.USERNAME)
                .param("email", TestAccount.EMAIL)
                .param("password", TestAccount.PASSWORD)
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(AccountController.PROFILE_URL + "*"))
                .andExpect(authenticated().withUsername(TestAccount.USERNAME))
        ;

        Account account = accountRepository.findByUsername(TestAccount.USERNAME).orElse(null);
        assertThat(account).isNotNull();
        assertThat(account.getId()).isNotNull();
        assertThat(account.getUsername()).isEqualTo(TestAccount.USERNAME);
        assertThat(account.getEmail()).isEqualTo(TestAccount.EMAIL);
        assertThat(account.getPassword()).isNotEqualTo(TestAccount.PASSWORD);
        assertThat(passwordEncoder.matches(TestAccount.PASSWORD, account.getPassword())).isTrue();
    }

    @Test
    void signup_invalid_rejectAndSignupPage() throws Exception {
        mockMvc.perform(post(AccountController.SIGNUP_URL)
                .param("username", TestAccount.USERNAME_INVALID)
                .param("email", TestAccount.EMAIL_INVALID)
                .param("password", TestAccount.PASSWORD_INVALID)
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(view().name(AccountController.SIGNUP_VIEW))
                .andExpect(model().attributeHasFieldErrors("signupForm", "username", "email", "password"))
                .andExpect(unauthenticated())
        ;

        Account account = accountRepository.findByUsername(TestAccount.USERNAME).orElse(null);
        assertThat(account).isNull();
    }

    @Test
    void profilePage() throws Exception {
        SignupForm signUpForm = TestAccount.createSignUpForm();
        String username = accountService.processSignup(signUpForm);

        mockMvc.perform(get(AccountController.PROFILE_URL + username))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(view().name(AccountController.PROFILE_VIEW))
        ;
    }

    @Test
    void profilePage_nonExistent_throwAndHandleException() throws Exception {
        String username = TestAccount.USERNAME_INVALID;

        mockMvc.perform(get(AccountController.PROFILE_URL + username))
                .andExpect(status().isOk())
                .andExpect(view().name(ExceptionAdvice.USERNAME_NOT_FOUND))
        ;
    }

    @Test
    void login_authenticated() throws Exception {
        SignupForm signUpForm = TestAccount.createSignUpForm();
        accountService.processSignup(signUpForm);

        mockMvc.perform(formLogin()
                .user(TestAccount.USERNAME).password(TestAccount.PASSWORD)
        )
                .andExpect(authenticated().withUsername(TestAccount.USERNAME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
        ;
    }

    @Test
    void login_nonExistent_unauthenticated() throws Exception {
        SignupForm signUpForm = TestAccount.createSignUpForm();
        accountService.processSignup(signUpForm);

        mockMvc.perform(formLogin()
                .user(TestAccount.USERNAME_INVALID).password(TestAccount.PASSWORD)
        )
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
        ;
    }

    @Test
    void login_invalidPassword_unauthenticated() throws Exception {
        SignupForm signUpForm = TestAccount.createSignUpForm();
        accountService.processSignup(signUpForm);

        mockMvc.perform(formLogin()
                .user(TestAccount.USERNAME).password(TestAccount.PASSWORD_INVALID)
        )
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
        ;
    }
}