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

    @GetMapping("/articleSubmitForm")
    public String articleSubmitFormGet(Model model) {
        model.addAttribute("article", new Article());
        return "articleSubmitForm";
    }
    @PostMapping("articleSubmitForm")
    public String articleSubmitFormPost(Article article) {
        article.setStatus("Submitted");
        articleRepository.save(article);
        return "redirect:/";
    }
}
