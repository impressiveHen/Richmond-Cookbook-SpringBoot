package com.richmond.cookbook.service;


import com.richmond.cookbook.entity.Recipe;
import com.richmond.cookbook.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe getById(int id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.get();
    }

    public void save(Recipe recipe) { recipeRepository.save(recipe); }

    public void deleteById(int id) { recipeRepository.deleteById(id); }
}
