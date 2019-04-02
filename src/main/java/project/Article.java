package project;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public enum Status {
        SUBMITTED,
        REVIEWED
    }
    private File file;

    private String status;

    public Article(String title) {
        this.title = title;
        this.status = Status.SUBMITTED.toString();
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

    public boolean setStatus(String status) {
        for(Status s : Article.Status.values()) {
            if (status.equals(s.toString())) {
                this.status = status;
                return true;
            }
        }
        return false;
    }

    public String getStatus() {
        return status;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

}
