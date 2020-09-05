package cf.scenecho.dinner.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserAccount(account);
    }

    @Transactional
    public String processSignup(SignupForm signUpForm) {
        Account account = createAccount(signUpForm);
        account = accountRepository.save(account);
        return login(account.getUsername(), signUpForm.getPassword());
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

    private String login(String username, String rawPassword) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, rawPassword);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return authentication.getName();
    }

    @Transactional
    public Account findAccount(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
