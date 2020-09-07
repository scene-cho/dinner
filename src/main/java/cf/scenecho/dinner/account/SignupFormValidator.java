package cf.scenecho.dinner.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SignupFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Autowired
    public SignupFormValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignupForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignupForm signupForm = (SignupForm) target;
        if (accountRepository.existsByUsername(signupForm.getUsername())) {
            errors.rejectValue("username", "duplicated.username", "이미 사용되는 닉네임입니다.");
        }
        if (accountRepository.existsByEmail(signupForm.getEmail())) {
            errors.rejectValue("email", "duplicated.email", "이미 사용되는 이메일입니다.");
        }
    }
}
