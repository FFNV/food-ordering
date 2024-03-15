package com.foodordering;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Taco {

    private Date createdAt = new Date();

    @NotNull
    @Size(min=5, message="Название должно иметь как минимум пять знаков")
    private String name;

    @Size(min=1, message="Необходимо выбрать как минимум один ингредиент")
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

}