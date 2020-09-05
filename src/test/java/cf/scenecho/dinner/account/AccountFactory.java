package cf.scenecho.dinner.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountFactory(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    public void createAndSaveAccount(String username) {
        SignupForm signUpForm = createSignupForm(username);
        Account account = createAccount(signUpForm);
        accountRepository.save(account);
    }

    private SignupForm createSignupForm(String username) {
        SignupForm signUpForm = new SignupForm();
        signUpForm.setUsername(username);
        signUpForm.setEmail(username + "@email.com");
        signUpForm.setPassword("password!");
        return signUpForm;
    }

    private Account createAccount(SignupForm signUpForm) {
        String username = signUpForm.getUsername();
        String email = signUpForm.getEmail();
        String password = encodePassword(signUpForm.getPassword());
        return new Account(username, email, password);
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}
