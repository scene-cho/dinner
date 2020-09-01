package cf.scenecho.dinner.account.controller;

import cf.scenecho.dinner.account.domain.SignUpForm;
import cf.scenecho.dinner.account.service.SignUpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired SignUpService signUpService;

    @Test
    void When_req_Should_profilePage() throws Exception {
        SignUpForm signUpForm = TestAccount.createSignUpForm();
        Long id = signUpService.processSignUp(signUpForm);

        mockMvc.perform(get(ProfileController.BASE_URL + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(view().name(ProfileController.PROFILE_VIEW))
        ;
    }

}