package cf.scenecho.dinner.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AccountController {
    public static final String SIGNUP_URL = "/signup";
    public static final String LOGIN_URL = "/login";
    public static final String PROFILE_URL = "/accounts/";

    static final String DIR = "accounts/";
    static final String SIGNUP_VIEW = DIR + "signup";
    static final String LOGIN_VIEW = DIR + "login";
    static final String PROFILE_VIEW = DIR + "profile";

    private final SignupFormValidator signupFormValidator;
    private final AccountService accountService;

    @Autowired
    public AccountController(SignupFormValidator accountValidator, AccountService accountService) {
        this.signupFormValidator = accountValidator;
        this.accountService = accountService;
    }

    @InitBinder("signupForm")
    void signupFormBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signupFormValidator);
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

    @GetMapping(LOGIN_URL)
    public String loginPage(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        HttpSession session = request.getSession();
        setPrevPage(session, referer);
        return LOGIN_VIEW;
    }

    private void setPrevPage(HttpSession session, String referer) {
        String refererInApplicationContext = parseReferer(referer);
        if (!refererInApplicationContext.startsWith(LOGIN_URL)) {
            session.setAttribute("prev", referer);
        }
    }

    private String parseReferer(String referer) {
        if (referer == null) return "/";
        String urlAfterScheme = referer.split("://")[1];
        String urlAfterApplicationRoot = (urlAfterScheme.split("/").length == 1) ? null : urlAfterScheme.split("/")[1];
        return "/" + urlAfterApplicationRoot;
    }

    @GetMapping(PROFILE_URL + "{username}")
    public String profilePage(@PathVariable String username, @CurrentUser Account currentUser, Model model) {
        Account foundAccount = accountService.findAccount(username);

        model.addAttribute("isOwner", foundAccount.is(currentUser));
        model.addAttribute("username", foundAccount.getUsername());
        model.addAttribute("email", foundAccount.getEmail());
        if (foundAccount.is(currentUser)) model.addAttribute("password", foundAccount.getPassword());

        return PROFILE_VIEW;
    }
}
