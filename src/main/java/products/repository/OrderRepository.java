package products.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import products.model.ProductOrder;
import products.model.User;

public interface OrderRepository
         extends CrudRepository<ProductOrder, Long> {

  List<ProductOrder> findByUserOrderByPlacedAtDesc(
          User user, Pageable pageable);

  /*
  List<Order> findByUserOrderByPlacedAtDesc(User user);
   */

}
