package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import project.storage.StorageService;

import java.util.stream.Collectors;

@Controller
public class EditorController {

    private final StorageService storageService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public EditorController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/editorView")
    public String editorViewGet(Model model, @CookieValue(value="userId", defaultValue="1") String userIdS) {
        long userId = Long.parseLong(userIdS);
        model.addAttribute("username", userRepository.findById(userId).getUsername());
        model.addAttribute("articles", articleRepository.findAll());
        model.addAttribute("files",
            storageService.loadAll()
            .map(path -> MvcUriComponentsBuilder
            .fromMethodName(ArticleController.class, "serveFile", path.getFileName().toString())
            .build().toString())
            .collect(Collectors.toList()));
        model.addAttribute("submittedArticles", articleRepository.findByStatus(Article.Status.SUBMITTED));
        model.addAttribute("reviewers", userRepository.findByRole(User.Role.REVIEWER));
        return "editorView";
    }

    @PostMapping("editorView")
    public String editorViewPost(@RequestParam("article") long articleId, @RequestParam("user") long userId) {
        try {
            User user = userRepository.findById(userId);
            user.addArticleId(articleId);
            userRepository.save(user);
            Article article = articleRepository.findById(articleId);
            article.setStatus(Article.Status.ASSIGNED);
            articleRepository.save(article);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/editorView";
    }

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}

