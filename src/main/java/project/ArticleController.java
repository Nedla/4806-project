package project;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.storage.StorageService;

@Controller
public class ArticleController {

    private final StorageService storageService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    public Article article = new Article();

    @Autowired
    public ArticleController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/articleSubmitForm")
    public String articleSubmitFormGet(Model model, @CookieValue(value="userId", defaultValue="1") String userIdS) {
        long userId = Long.parseLong(userIdS);
        model.addAttribute("username", userRepository.findById(userId).getUsername());
        model.addAttribute("article", this.article);
        model.addAttribute("files", storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(
                ArticleController.class, "serveFile", path.getFileName().toString())
                .build().toString())
                .collect(Collectors.toList()));
        return "articleSubmitForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("articleSubmitForm")
    public String articleSubmitFormPost(HttpServletRequest servletRequest, @RequestParam("file") MultipartFile file, 
    RedirectAttributes redirectAttributes, @RequestParam("title") String title) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());

        Article article = new Article(title);
        article.setStatus(Article.Status.SUBMITTED);
        article.setFile(file);
        articleRepository.save(article);

        return "redirect:/";
    }
    
}
