package products.data;

import org.springframework.data.repository.CrudRepository;

import products.Ingredient;

public interface IngredientRepository 
         extends CrudRepository<Ingredient, String> {

}
