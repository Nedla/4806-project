package project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void initialValuesTest() {
        List<User> users = userRepository.findByUsernameAndPassword("editor", "pass");
        Assert.notEmpty(users, "List should not be empty");
        Assert.isTrue(users.size() == 1, "Should only find 1 user");
        Assert.isTrue(users.get(0).getRole().equals(User.Role.EDITOR.toString()), "Should have editor role");

        int i = 0;
        for (User user : userRepository.findAll()) {
            Assert.isTrue(user.getPassword().equals("pass"), "All users should have password 'pass'");
            i++;
        }
        Assert.isTrue(i==3, "Should contain 3 users");
    }

    @Test
    public void repoSaveTest() {
        userRepository.save(new User("testuser", "testpass", User.Role.REVIEWER.toString()));

        List<User> users = userRepository.findByUsernameAndPassword("testuser", "testpass");
        Assert.isTrue(users.size() == 1, "Should find 1 user");
    }
}
