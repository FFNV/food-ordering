package products.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import products.ProductOrder;
import products.User;

public interface OrderRepository
         extends CrudRepository<ProductOrder, Long> {

  List<ProductOrder> findByUserOrderByPlacedAtDesc(
          User user, Pageable pageable);

  /*
  List<Order> findByUserOrderByPlacedAtDesc(User user);
   */

}
