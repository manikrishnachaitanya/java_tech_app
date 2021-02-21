package com.rezdy.lunch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezdy.lunch.entity.Ingredient;
import com.rezdy.lunch.service.LunchService;
import com.rezdy.lunch.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class LunchController {

    @Autowired
    private LunchService lunchService;


    @Autowired
    private  ObjectMapper objectMapper;

    @GetMapping("/lunch")
    public @ResponseBody List<Recipe> getRecipes(@RequestParam(value = "date") String date) {
        return lunchService.getNonExpiredRecipesOnDate(LocalDate.parse(date));
    }

    /*@PostMapping("/recipe/save")
    public Recipe saveRecipe(@RequestBody Recipe recipe) {

        return lunchService.saveRecipe(recipe);
    }

    @GetMapping("/recipe/all")
    public void getAllIngredients(@RequestBody String ingredient) {

        return ;
    }*/
}
