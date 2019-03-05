package project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /*
    @GetMapping("/")
    public String greeting() {
        return "index";
    }
    */

    @GetMapping("/author")
    public String getAuthorPage() {
        return "author";
    }

    @GetMapping("/editor")
    public String getEditorPage() {
        return "editor";
    }

    @GetMapping("/reviewer")
    public String getReviewerPage() {
        return "reviewer";
    }

}
