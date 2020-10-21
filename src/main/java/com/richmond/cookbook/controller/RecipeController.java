package com.richmond.cookbook.controller;

import com.richmond.cookbook.entity.Recipe;
import com.richmond.cookbook.entity.User;
import com.richmond.cookbook.service.RecipeService;
import com.richmond.cookbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipeController {
    private static Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @RequestMapping(value="/recipe/edit", method=RequestMethod.GET)
    public String showEditRecipePage(@RequestParam(value="id", required=false) String id, ModelMap model) {
        Recipe recipe;
        if (id == null) {
            recipe = new Recipe();
        } else {
            recipe = recipeService.getById(Integer.valueOf(id));
            model.addAttribute("id", id);
        }
        model.addAttribute("recipe", recipe);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        model.addAttribute("user", user);

        return "recipe/edit";
    }

    @RequestMapping(path="/recipe/save", method=RequestMethod.POST)
    public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "recipe/edit";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        if (!recipe.getImage().startsWith(endpointUrl)) {
            recipe.setImage("");
        }

        List<Recipe> userRecipes = user.getRecipes();
        if (recipe.getId()==null) {
            user.addRecipe(recipe);
        } else {
            for (int i=0; i<userRecipes.size(); i++) {
                if (userRecipes.get(i).getId() == recipe.getId())
                    user.updateRecipe(recipe, i);
            }
        }

        userService.saveUser(user);
        return "redirect:/user/profile";
    }

    @RequestMapping(path="/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable(name="id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        Recipe recipe = recipeService.getById(id);
        userService.deleteRecipe(user, recipe);
        return "redirect:/user/profile";
    }
}
