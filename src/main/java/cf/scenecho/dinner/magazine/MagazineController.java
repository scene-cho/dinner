package cf.scenecho.dinner.magazine;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MagazineController {

    public static final String URL = "/magazines/";

    static final String DIR = "board/";
    static final String VIEW = DIR + "magazine";

    @GetMapping(URL + "{magazineName}")
    public String magazinePage(@PathVariable String magazineName, Model model) {
        model.addAttribute("magazineName", magazineName);
        return VIEW;
    }
}
