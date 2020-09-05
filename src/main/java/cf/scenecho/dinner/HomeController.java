package cf.scenecho.dinner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    public static final String HOME_URL = "/";
    public static final String ABOUT_URL = "/about";

    static final String HOME_VIEW = "home";
    static final String ABOUT_VIEW = "about";

    @GetMapping(HOME_URL)
    public String home() {
        return HOME_VIEW;
    }

    @GetMapping(ABOUT_URL)
    public String about() {
        return ABOUT_VIEW;
    }
}
