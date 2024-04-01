package products.data;
import org.springframework.data.repository.CrudRepository;
import products.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
