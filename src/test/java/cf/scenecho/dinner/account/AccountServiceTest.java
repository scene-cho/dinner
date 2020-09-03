package cf.scenecho.dinner.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AccountServiceTest {

    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;

    @AfterEach
    void clearRepository() {
        accountRepository.deleteAll();
    }

    @Test
    void loadUserByUsername() {
        SignupForm signUpForm = TestAccount.createSignUpForm();
        String username = accountService.processSignup(signUpForm);

        UserDetails user = accountService.loadUserByUsername(username);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(signUpForm.getUsername());
    }

    @Test
    void loadUserByUsername_nonExistent_throwException() {
        String username = TestAccount.USERNAME_INVALID;

        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername(username);
        });
    }

}