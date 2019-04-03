package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewerController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/reviewerView")
    public String reviewerViewGet(@CookieValue(value="userId", defaultValue="1") String userIdS, Model model) {
        long userId = Long.parseLong(userIdS);
        if (userId == -1) return "error";

        User user = userRepository.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());

        List<Article> articles = new ArrayList<>();
        for(Long id : user.getArticleIds()) {
            articles.add(articleRepository.findById(id).get());
        }
        model.addAttribute("articles", articles);
        model.addAttribute("statuses", Article.Status.values());
        return "reviewerView";
    }

    @PostMapping("/reviewerView")
    public String reviewerViewPost(@CookieValue(value="userId", defaultValue="0") String userIdS, @RequestParam("article") long articleId, @RequestParam("status") Article.Status status) {
        long userId = Long.parseLong(userIdS);

        Article article = articleRepository.findById(articleId);
        article.setStatus(status);
        articleRepository.save(article);

        //Remove article from user collection if no longer under review
        if (!status.equals(Article.Status.ASSIGNED)) {
            User user = userRepository.findById(userId);
            try {
                user.deleteArticle(articleId);
            } catch (Exception e) {}
            userRepository.save(user);
        }

        return "redirect:/reviewerView";
    }
}
