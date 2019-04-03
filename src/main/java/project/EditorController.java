package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.User.Role;
import project.storage.StorageService;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
    public String editorViewGet(Model model) {
        model.addAttribute("articles", getAllArticles());
        model.addAttribute("files",
                storageService.loadAll()
                        .map(path -> MvcUriComponentsBuilder
                                .fromMethodName(ArticleController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                        .collect(Collectors.toList()));
        model.addAttribute("reviewers", findByRole(User.Role.REVIEWER));
        return "editorView";
    }

    @PostMapping("editorView")
    public String editorViewPost(@RequestParam("article") long articleId, @RequestParam("user") long userId) {
        try {
            userRepository.findById(userId).addArticleId(articleId);
            articleRepository.findById(articleId).setStatus(Article.Status.ASSIGNED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/editorView";
    }

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Iterable<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }
}
