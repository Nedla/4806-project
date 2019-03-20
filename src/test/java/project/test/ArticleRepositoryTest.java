package project.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import project.Article;
import project.ArticleRepository;
import project.User;
import project.UserRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void initialValuesTest() {
        List<Article> articles = articleRepository.findByTitle("article1");
        Assert.notEmpty(articles, "List should not be empty");
        Assert.isTrue(articles.size() == 1, "Should find 1 article");

        int i = 0;
        for (Article article : articleRepository.findAll()) {
            Assert.isTrue(article.getStatus().equals(Article.Status.SUBMITTED.toString()), "All articles start with status SUBMITTED");
            i++;
        }
        Assert.isTrue(i==3, "Should contain 3 articles");
    }

    @Test
    public void repoSaveTest() {
        articleRepository.save(new Article("testarticle"));

        List<Article> articles = articleRepository.findByTitle("testarticle");
        Assert.isTrue(articles.size() == 1, "Should find 1 article");
    }
}
