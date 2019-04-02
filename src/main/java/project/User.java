package project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Role {
        SUBMITTER,
        REVIEWER,
        EDITOR
    }

    private String username;
    private String password;
    private String role;


    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        setRole(role);
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

    public String getRole() {
        return role;
    }

    public boolean setRole(String role) {
        for(Role r : User.Role.values()) {
            if (role.equals(r.toString())) {
                this.role = role;
                return true;
            }
        }
        return false;
    }
}
