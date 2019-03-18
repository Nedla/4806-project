package project;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.storage.StorageService;
import project.storage.StorageFileNotFoundException;

@Controller
public class ArticleController {

    private final StorageService storageService;

    @Autowired
    public ArticleController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/articleSubmitForm")
    public String articleSubmitFormGet(Model model) throws IOException {
        model.addAttribute("article", new Article());
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(ArticleController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "articleSubmitForm";
    }
    @PostMapping("articleSubmitForm")
    public String articleSubmitFormPost(Article article) {
        article.setStatus("Submitted");
        articleRepository.save(article);
        return "redirect:/";
    }
}
