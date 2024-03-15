package com.foodordering;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class Taco {

    @Id
    private Long id;
    private Date createdAt = new Date();

    @NotNull
    @Size(min=5, message="Название должно иметь как минимум пять знаков")
    private String name;


    @Size(min=1, message="Необходимо выбрать как минимум один ингредиент")
    private List<Ingredient> ingredients = new ArrayList<>();

}