package cf.scenecho.dinner.account.controller;

import cf.scenecho.dinner.account.domain.Account;
import cf.scenecho.dinner.account.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    public static final String BASE_URL = "/accounts/";

    static final String URL = "/accounts/{id}";
    static final String PROFILE_VIEW = "accounts/profile";

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(URL)
    public String showProfileRequest(@PathVariable Long id, Model model) {
        Account account = profileService.findAccount(id);
        model.addAttribute(account);
        return PROFILE_VIEW;
    }

}
