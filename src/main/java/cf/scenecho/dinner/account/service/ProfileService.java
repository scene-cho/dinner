package cf.scenecho.dinner.account.service;

import cf.scenecho.dinner.account.domain.Account;
import cf.scenecho.dinner.account.domain.AccountRepository;
import cf.scenecho.dinner.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final AccountRepository accountRepository;

    @Autowired
    public ProfileService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account findAccount(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UserNotFoundException();
        }
        return account;
    }
}
