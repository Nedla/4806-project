package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewerController {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/reviewerView")
    public String reviewerViewGet(@CookieValue(value="userId", defaultValue="-1") String userIdS, Model model) {
        long userId = Long.parseLong(userIdS);
        if (userId == -1) return "error";

        User user = userRepository.findById(userId);
        model.addAttribute("user", user);

        List<Article> articles = new ArrayList<>();
        for(Long id : user.getArticleIds()) {
            articles.add(articleRepository.findById(id).get());
        }
        model.addAttribute("articles", articles);
        return "reviewerView";
    }
}
