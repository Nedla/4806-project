package project;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.storage.StorageService;
import project.storage.StorageFileNotFoundException;

@Controller
public class ArticleController {

    private final StorageService storageService;

    @Autowired
    ArticleRepository articleRepository;

    public Article article = new Article();

    @Autowired
    public ArticleController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/articleSubmitForm")
    public String articleSubmitFormGet(Model model) throws IOException {
        model.addAttribute("article", new Article());

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
    RedirectAttributes redirectAttributes, Article article) {

        String fileName = file.getOriginalFilename();
        File articleFile = new File(servletRequest.getServletContext().getRealPath("/article"), fileName);

        try
        {
            file.transferTo(articleFile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());

        article.setStatus("Submitted");
        article.setArticle(articleFile);
        articleRepository.save(article);

        return "redirect:/";
    }
    
}
