package products.data;

import org.springframework.data.repository.CrudRepository;

import products.Product;

public interface ProductRepository
         extends CrudRepository<Product, Long> {

}
