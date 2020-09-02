package cf.scenecho.dinner.account.controller;

import cf.scenecho.dinner.account.domain.SignUpForm;
import cf.scenecho.dinner.account.service.SignUpService;
import cf.scenecho.dinner.exception.ExceptionAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired SignUpService signUpService;
    @Autowired EntityManager entityManager;

    @Test
    void When_req_Should_profilePage() throws Exception {
        SignUpForm signUpForm = TestAccount.createSignUpForm();
        String username = signUpService.processSignUp(signUpForm);

        mockMvc.perform(get(ProfileController.BASE_URL + username))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(view().name(ProfileController.PROFILE_VIEW))
        ;
    }

    @Test
    void When_wrongReq_Should_throw() throws Exception {
        String username = TestAccount.USERNAME_INVALID;

        mockMvc.perform(get(ProfileController.BASE_URL + username))
                .andExpect(status().isOk())
                .andExpect(view().name(ExceptionAdvice.BAD_REQUEST_PAGE))
        ;
    }

}