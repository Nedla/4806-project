package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    public User user = new User();

    @GetMapping("/loginPage")
    public String loginPageGet(Model model) {
        model.addAttribute("user", this.user);
        return "loginPage";
    }
    @PostMapping("/loginPage")
    public String loginPagePost(User user, HttpServletResponse response) {
        List<User> users = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (users.isEmpty()) {
            return "redirect:/"; //TODO return message 'username/password incorrect'
        } else if (users.size() > 1) {
            return "error"; //TODO multiple users can have same user/pass, should prevent this
        }
        response.addCookie(new Cookie("role", users.get(0).getRole().toString()));
        return "redirect:/";
    }

    @GetMapping("/registerUserForm")
    public String registerUserFormGet(Model model) {
        model.addAttribute("user", this.user);
        model.addAttribute("roles", Role.values());
        return "registerUserForm";
    }
    @PostMapping("/registerUserForm")
    public String registerUserFormPost(User user, HttpServletResponse response) {
        userRepository.save(user);
        response.addCookie(new Cookie("role", user.getRole().toString()));
        return "redirect:/";
    }
}
