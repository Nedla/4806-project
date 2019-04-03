package project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import project.Article.Status;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "articles", path = "articles")
public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByTitle(String title);
    List<Article> findByStatus(Status status);
    Article findById(long id);
}
