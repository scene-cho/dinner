package cf.scenecho.dinner.account;

import cf.scenecho.dinner.infra.AbstractContainerBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest extends AbstractContainerBaseTest {

    static final String USERNAME = "scene";

    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;
    @Autowired AccountFactory accountFactory;

    @AfterEach
    void clearRepository() {
        accountRepository.deleteAll();
    }

    @Test
    void loadUserByUsername() {
        accountFactory.createAndSaveAccount(USERNAME);

        UserDetails user = accountService.loadUserByUsername(USERNAME);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void loadUserByUsername_nonExistent_throwException() {
        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername(USERNAME);
        });
    }

}