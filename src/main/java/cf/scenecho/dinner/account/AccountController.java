package cf.scenecho.dinner.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AccountController {
    public static final String SIGNUP_URL = "/signup";
    public static final String PROFILE_URL = "/accounts/";

    static final String DIR = "accounts/";
    static final String SIGNUP_VIEW = DIR + "form";
    static final String PROFILE_VIEW = DIR + "profile";

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(SIGNUP_URL)
    public String signupPage(Model model) {
        model.addAttribute(new SignupForm());
        return SIGNUP_VIEW;
    }

    @PostMapping(SIGNUP_URL)
    public String signup(@Valid SignupForm signupForm, Errors errors) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW;
        }
        String username = accountService.processSignup(signupForm);
        return "redirect:" + PROFILE_URL + username;
    }

    @GetMapping(PROFILE_URL + "{username}")
    public String profilePage(@PathVariable String username, Model model) {
        Account account = accountService.findAccount(username);
        model.addAttribute(account);
        return PROFILE_VIEW;
    }

}