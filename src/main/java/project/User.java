package project;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.List;

@Entity
public class User {
    public enum Role {
        SUBMITTER,
        REVIEWER,
        EDITOR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    
    private String username;
    private String password;

    @ElementCollection
    private List<Long> articleIds;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Long> getArticleIds() {
        return this.articleIds;
    }

    public void setArticleIds(List<Long> ids) throws Exception {
        if (this.role.equals(User.Role.REVIEWER)) {
            this.articleIds = ids;
        } else {
            throw new Exception("User has an invalid role - Must be reviewer to be assigned articles");
        }
    }

    public void addArticleId(Long id) throws Exception {
        if (!(this.articleIds.contains(id)) && this.role.equals(User.Role.REVIEWER)) {
            this.articleIds.add(id);
        } else {
            throw new Exception("User has an invalid role OR the article is already assigned");
        }
    }

    public void deleteArticle(long id) throws Exception {
        for (Long i : this.articleIds) {
            if (i.equals(id)) {
                this.articleIds.remove(i);
                return;
            }
        }
        throw new Exception("ArticleID not found");
    }
}

