package project;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.net.URL;
import java.util.List;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testLoginPageGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/loginPage"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userController.user))
                .andExpect(view().name("loginPage"));
    }

    @Test
    public void testLoginPagePost() throws Exception {
        Gson gson = new Gson();
        User user = new User("name123", "pass123", User.Role.SUBMITTER.toString());
        List<User> users = userRepository.findByUsernameAndPassword("name123", "pass123");
        Assert.isTrue(users.isEmpty(), "User does not exist yet");

        mockMvc.perform(MockMvcRequestBuilders.post("/loginPage")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("redirect:/"));

        //TODO Repo check for update
        //users = userRepository.findByUsernameAndPassword("name123", "pass123");
        //Assert.isTrue(users.size() == 1, "User should be registered now");
    }

    @Test
    public void testRegisterUserFormGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registerUserForm"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userController.user))
                .andExpect(model().attribute("roles", User.Role.values()))
                .andExpect(view().name("registerUserForm"));
    }

}
