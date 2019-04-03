package project;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Test
    public void testConstructor() throws Exception {
        User user = new User();
        Assert.assertNull(user.getUsername());
        Assert.assertNull(user.getPassword());
        Assert.assertNull(user.getRole());
        Assert.assertNull(user.getId());

        user = new User("name1", "pass1", User.Role.SUBMITTER);
        Assert.assertEquals("name1", user.getUsername());
        Assert.assertEquals( "pass1", user.getPassword());
        Assert.assertEquals( User.Role.SUBMITTER, user.getRole());
        Assert.assertNull(user.getId());
    }

    @Test
    public void testId() throws Exception {
        User user = new User();
        user.setId((long)42);
        Assert.assertEquals((long)42, (long)user.getId());
    }

    @Test
    public void testUsername() throws Exception {
        User user = new User();
        user.setUsername("name2");
        Assert.assertEquals("name2", user.getUsername());
    }

    @Test
    public void testPassword() throws Exception {
        User user = new User();
        user.setPassword("pass2");
        Assert.assertEquals("pass2", user.getPassword());
    }

    @Test
    public void testRole() throws Exception {
        User user = new User("name1", "pass1", User.Role.EDITOR);
        Assert.assertEquals(User.Role.EDITOR, user.getRole());

        user.setRole(User.Role.REVIEWER);
        Assert.assertEquals(User.Role.REVIEWER, user.getRole());

    }
}
