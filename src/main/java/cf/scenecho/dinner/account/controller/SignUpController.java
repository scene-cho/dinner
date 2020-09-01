package cf.scenecho.dinner.account.controller;

import cf.scenecho.dinner.account.domain.SignUpForm;
import cf.scenecho.dinner.account.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignUpController {
    public static final String URL = "/accounts";

    static final String FORM_VIEW = "accounts/form";

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping(URL)
    public String formRequest(Model model) {
        model.addAttribute(new SignUpForm());
        return FORM_VIEW;
    }

    @PostMapping(URL)
    public String signUpRequest(@Valid SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return FORM_VIEW;
        }
        Long accountId = signUpService.processSignUp(signUpForm);
        return "redirect:" + ProfileController.BASE_URL + accountId;
    }
}
