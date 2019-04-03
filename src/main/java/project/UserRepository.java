package project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import project.Role;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByUsernameAndPassword(String username, String password);
    List<User> findByUsername(String username);
    List<User> findByRole(Role role);
}
