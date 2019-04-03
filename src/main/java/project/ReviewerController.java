package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewerController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/reviewerView")
    public String reviewerViewGet(Model model) {
        //TODO select articles assigned to the given reviewer
        
        return "reviewerView";
    }
}
