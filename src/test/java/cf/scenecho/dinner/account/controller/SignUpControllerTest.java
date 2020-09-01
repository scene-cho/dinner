package cf.scenecho.dinner.account.controller;

import cf.scenecho.dinner.account.domain.Account;
import cf.scenecho.dinner.account.domain.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccountRepository accountRepository;

    @AfterEach
    void clearRepository() {
        accountRepository.deleteAll();
    }

    @Test
    void When_req_Should_signUpPage() throws Exception {
        mockMvc.perform(get(SignUpController.URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(SignUpController.FORM_VIEW))
                .andExpect(model().attributeExists("signUpForm"))
        ;
    }

    @Test
    void When_submit_Should_process() throws Exception {
        mockMvc.perform(post(SignUpController.URL)
                .param("username", TestAccount.USERNAME)
                .param("email", TestAccount.EMAIL)
                .param("password", TestAccount.PASSWORD)
                .with(csrf())
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(ProfileController.BASE_URL + "*"))
        ;

        Account account = accountRepository.findByUsername(TestAccount.USERNAME);
        assertThat(account).isNotNull();
        assertThat(account.getId()).isNotNull();
        assertThat(account.getUsername()).isEqualTo(TestAccount.USERNAME);
        assertThat(account.getEmail()).isEqualTo(TestAccount.EMAIL);
        assertThat(account.getPassword()).isNotEqualTo(TestAccount.PASSWORD);
    }

    @Test
    void When_wrongSubmit_Should_reject() throws Exception {
        mockMvc.perform(post(SignUpController.URL)
                .param("username", TestAccount.USERNAME_INVALID)
                .param("email", TestAccount.EMAIL_INVALID)
                .param("password", TestAccount.PASSWORD_INVALID)
                .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(SignUpController.FORM_VIEW))
                .andExpect(model().attributeHasFieldErrors("signUpForm", "username", "email", "password"))
        ;

        Account account = accountRepository.findByUsername(TestAccount.USERNAME);
        assertThat(account).isNull();
    }
}