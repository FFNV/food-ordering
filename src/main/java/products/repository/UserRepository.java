package products.repository;
import org.springframework.data.repository.CrudRepository;
import products.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
