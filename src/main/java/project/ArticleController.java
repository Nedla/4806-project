package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    public Article article = new Article();

    @GetMapping("/articleSubmitForm")
    public String articleSubmitFormGet(Model model) {
        model.addAttribute("article", this.article);
        return "articleSubmitForm";
    }
    @PostMapping("articleSubmitForm")
    public String articleSubmitFormPost(Article article) {
        article.setStatus(Article.Status.SUBMITTED.toString());
        articleRepository.save(article);
        return "redirect:/";
    }
}
