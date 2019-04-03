package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import project.storage.StorageService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EditorController {

    private final StorageService storageService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    public EditorController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/editorView")
    public String editorViewGet(Model model, @CookieValue(value="role", defaultValue="none") String roleCookie) {
        if (!(roleCookie.equals(User.Role.EDITOR.toString()))) {
            return "redirect:/loginPage";
        }
        model.addAttribute("articles", getAllArticles());
        model.addAttribute("files", storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(
                ArticleController.class, "serveFile", path.getFileName().toString())
                .build().toString())
                .collect(Collectors.toList()));
        return "editorView";
    }

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
