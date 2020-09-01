package cf.scenecho.dinner.account.service;

import cf.scenecho.dinner.account.domain.Account;
import cf.scenecho.dinner.account.domain.AccountRepository;
import cf.scenecho.dinner.account.domain.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String processSignUp(SignUpForm signUpForm) {
        Account account = createAccount(signUpForm);
        account = accountRepository.save(account);
        return account.getUsername();
    }

    private Account createAccount(SignUpForm signUpForm) {
        String username = signUpForm.getUsername();
        String email = signUpForm.getEmail();
        String password = signUpForm.getPassword();
        password = passwordEncoder.encode(password);
        return new Account(username, email, password);
    }

}
