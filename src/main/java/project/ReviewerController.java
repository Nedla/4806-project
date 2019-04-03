package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewerController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/reviewerView")
    public String reviewerViewGet(Model model, @CookieValue(value="role", defaultValue="none") String roleCookie) {
        if (!(roleCookie.equals(User.Role.REVIEWER.toString()))) {
            return "redirect:/loginPage";
        }

        //TODO select articles assigned to the given reviewer
        
        return "reviewerView";
    }
}
