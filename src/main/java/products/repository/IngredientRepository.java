package products.repository;

import org.springframework.data.repository.CrudRepository;

import products.model.Ingredient;

public interface IngredientRepository 
         extends CrudRepository<Ingredient, String> {

}
