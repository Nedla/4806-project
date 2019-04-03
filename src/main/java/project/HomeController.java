package project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/greeting")
    public @ResponseBody String greeting() {
        return "Hello World";
    }

    @GetMapping("/")
    public String checkRole(@CookieValue(value="role", defaultValue="none") String roleCookie) {
        if (roleCookie.equals("none")) {
            return "redirect:/loginPage";
        } else if (roleCookie.equals(User.Role.SUBMITTER.toString())) {
            return "redirect:/articleSubmitForm";
        } else if (roleCookie.equals(User.Role.REVIEWER.toString())) {
            return "redirect:/reviewerView";
        } else if (roleCookie.equals(User.Role.EDITOR.toString())) {
            return "redirect:/editorView";
        } else {
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logoutGet(HttpServletResponse response) {
        response.addCookie(new Cookie("role", "none"));
        response.addCookie(new Cookie("userId", "-1"));
        return "redirect:/";
    }
}
