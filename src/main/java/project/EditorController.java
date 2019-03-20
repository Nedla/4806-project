package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class EditorController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/editorView")
    public String editorViewGet(Model model) {
        model.addAttribute("articles", getAllArticles());
        return "editorView";
    }

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
