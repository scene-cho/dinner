package cf.scenecho.dinner.account.service;

import cf.scenecho.dinner.account.domain.Account;
import cf.scenecho.dinner.account.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProfileService {

    private final AccountRepository accountRepository;

    @Autowired
    public ProfileService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account findAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(NoSuchElementException::new);
    }
}
