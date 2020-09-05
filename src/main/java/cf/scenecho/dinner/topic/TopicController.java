package cf.scenecho.dinner.topic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TopicController {
    public static final String URL = "/topics/";

    static final String DIR = "board/";
    static final String VIEW = DIR + "topic";

    @GetMapping(URL + "{topicName}")
    public String topicPage(@PathVariable String topicName, Model model) {
        model.addAttribute("topicName", topicName);
        return VIEW;
    }
}
