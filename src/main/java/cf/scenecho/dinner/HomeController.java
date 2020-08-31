package cf.scenecho.dinner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    public static final String URL = "/";

    @GetMapping(URL)
    public String home() {
        return "home";
    }

}
