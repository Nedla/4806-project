package project;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    //TODO make enum instead of string?
    private String status;

    //TODO add file storage (as file on disk or in memory?)

    public Article(String title) {
        this.title = title;
        this.status = "Submitted";
    }
    public Article() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    /*
    public void setArticle(MultipartFile article) {
        this.article = article;
    }

    public MultipartFile getArticle() {
        return article;
    }
    */

}
