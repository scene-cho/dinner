package cf.scenecho.dinner.article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {
    public static final String URL = "/articles/";

    static final String DIR = "board/";
    static final String VIEW = DIR + "article";

    @GetMapping(URL + "{articleName}")
    public String profilePage(@PathVariable String articleName, Model model) {
        model.addAttribute("articleName", articleName);
        return VIEW;
    }
}
