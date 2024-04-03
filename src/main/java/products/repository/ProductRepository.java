package products.repository;

import org.springframework.data.repository.CrudRepository;

import products.model.Product;

public interface ProductRepository
         extends CrudRepository<Product, Long> {

}
