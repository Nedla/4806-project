package project;





import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "articles", path = "articles")
public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByTitle(String title);
}
