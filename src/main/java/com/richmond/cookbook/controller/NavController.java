package com.richmond.cookbook.controller;

import com.richmond.cookbook.entity.Recipe;
import com.richmond.cookbook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NavController {
    @Autowired
    RecipeService recipeService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home(ModelMap model) {
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<parsedRecipe> parsedRecipes = recipes.stream().map(r->new parsedRecipe(r)).collect(Collectors.toList());

        model.addAttribute("parsedRecipes", parsedRecipes);

        return "home";
    }

    private class parsedRecipe {
        public String title;
        public String author;
        public String category;
        public int difficulty;
        public String image;
        public List<String> steps;

        public parsedRecipe(Recipe recipe) {
            this.title = recipe.getName();
            this.author = recipe.getUser().getUserName();
            this.category = recipe.getCategory();
            this.difficulty = recipe.getDifficulty();
            this.image = recipe.getImage();
            this.steps = recipe.getSteps().stream().map(s->s.getStep().replace(',', '@')).collect(Collectors.toList());
        }
    }
}
