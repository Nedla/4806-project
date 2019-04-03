package project;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Article {
    public enum Status {
        SUBMITTED,
        ASSIGNED,
        REVIEWED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    private File file;

    public Article(String title) {
        this.title = title;
        this.status = Status.SUBMITTED;
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


    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setFile(MultipartFile file) {
        this.file = new File(file.getOriginalFilename());
        try {
            file.transferTo(this.file);
        } catch (Exception e) {
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
