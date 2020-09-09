package cf.scenecho.dinner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    public static final String HOME_URL = "/";
    public static final String ABOUT_URL = "/about";

    static final String HOME_VIEW = "home";
    static final String ABOUT_VIEW = "about";

    private final String springProfile;

    @Autowired
    public HomeController(String springProfile) {
        this.springProfile = springProfile;
    }

    @GetMapping(HOME_URL)
    public String home(Model model) {
        String springProfile = String.format("Active profile is [ %s ].", this.springProfile);
        model.addAttribute("springProfile", springProfile);
        return HOME_VIEW;
    }

    @GetMapping(ABOUT_URL)
    public String about() {
        return ABOUT_VIEW;
    }
}
