package cf.scenecho.dinner.account;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final AccountFactory accountFactory;
    private final AccountService accountService;

    public WithAccountSecurityContextFactory(AccountFactory accountFactory, AccountService accountService) {
        this.accountFactory = accountFactory;
        this.accountService = accountService;
    }

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String username = withAccount.value();
        accountFactory.createAndSaveAccount(username);

        UserDetails principal = accountService.loadUserByUsername(username);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        authenticatePrincipal(principal, context);

        return context;
    }

    private void authenticatePrincipal(UserDetails principal, SecurityContext context) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(authentication);
    }

}
