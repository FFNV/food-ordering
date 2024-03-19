package com.foodordering.data;

import com.foodordering.Ingredient;
import static org.assertj.core.api.Assertions.assertThat;

import com.foodordering.Ingredient.Type;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IngredientRepositoryTests {

    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    public void findById() {
        Optional<Ingredient> flto = ingredientRepository.findById("FLTO");
        assertThat(flto.isPresent()).isTrue();
        assertThat(flto.get()).isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));

        Optional<Ingredient> xxxx = ingredientRepository.findById("XXXX");
        assertThat(xxxx.isEmpty()).isTrue();
    }

}
