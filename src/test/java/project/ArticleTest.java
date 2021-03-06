package project;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTest {

    @Test
    public void testConstructor() {
        Article article = new Article();
        Assert.assertNull(article.getTitle());
        Assert.assertNull(article.getStatus());
        Assert.assertNull(article.getFile());
        Assert.assertNull(article.getId());

        article = new Article("title1");
        Assert.assertEquals("title1", article.getTitle());
        Assert.assertEquals( Article.Status.SUBMITTED, article.getStatus());
        Assert.assertNull(article.getFile());
        Assert.assertNull(article.getId());
    }

    @Test
    public void testId() {
        Article article = new Article();
        article.setId((long)42);
        Assert.assertEquals((long)42, (long)article.getId());
    }

    @Test
    public void testTitle() {
        Article article = new Article();
        article .setTitle("title2");
        Assert.assertEquals("title2", article.getTitle());
    }

    @Test
    public void testStatus() {
        Article article = new Article("title1");
        Assert.assertEquals(Article.Status.SUBMITTED, article.getStatus());

        article.setStatus(Article.Status.REJECTED);
        Assert.assertEquals(Article.Status.REJECTED, article.getStatus());

    }
}

