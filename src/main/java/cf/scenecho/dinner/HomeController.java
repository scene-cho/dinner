package cf.scenecho.dinner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    public static final String URL = "/";

    static final String VIEW_NAME = "home";

    @GetMapping(URL)
    public String home(Model model, Principal principal) {
        String welcomeMessage = "Welcome!";
        if (principal != null) {
            welcomeMessage += principal.getName();
        }
        model.addAttribute("welcomeMessage", welcomeMessage);
        return VIEW_NAME;
    }

}
