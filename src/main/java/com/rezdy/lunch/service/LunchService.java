package com.rezdy.lunch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rezdy.lunch.entity.Ingredient;
import com.rezdy.lunch.entity.Recipe;

@Service
public class LunchService {

    @Autowired
    private EntityManager entityManager;

    private List<Recipe> recipesSorted;

    public List<Recipe> getNonExpiredRecipesOnDate(LocalDate date) {
        List<Recipe> recipes = loadRecipes(date);

        sortRecipes(recipes);

        return recipesSorted;
    }

    private void sortRecipes(List<Recipe> recipes) {
        recipesSorted = recipes; //TODO sort recipes considering best-before
    }

    public List<Recipe> loadRecipes(LocalDate date) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = cb.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);

        CriteriaQuery<Recipe> query = criteriaQuery.select(recipeRoot);

        /*Subquery<Recipe> nonExpiredIngredientSubquery = query.subquery(Recipe.class);
        Root<Recipe> nonExpiredIngredient = nonExpiredIngredientSubquery.from(Recipe.class);
        nonExpiredIngredientSubquery.select(nonExpiredIngredient);

        Predicate matchingRecipe = cb.equal(nonExpiredIngredient.get("title"), recipeRoot.get("title"));
        Predicate expiredIngredient = cb.greaterThan(nonExpiredIngredient.join("ingredients").get("useBy"), date);

        Predicate allNonExpiredIngredients = cb.exists(nonExpiredIngredientSubquery.where(matchingRecipe, expiredIngredient));*/

        List<Recipe> recipeList =  entityManager.createQuery(query).getResultList();

        System.out.println("recipeList :: " + recipeList);

        recipeList.forEach(recipe -> System.out.println("Recipe Title ::: " +recipe));

        List<Recipe> validRecipes = recipeList.stream().filter(recipe -> !isExpired(recipe, date))
                .collect(Collectors.toList());

        return validRecipes;

    }

    public boolean isExpired(Recipe recipe, LocalDate date)
    {
        boolean isExpired = false;

        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getUseBy().isBefore(date)).findAny();
        if (optionalIngredient.isPresent())
        {
            isExpired = true;
        }
        return isExpired;
    }

    public List<Ingredient> loadIngredients(LocalDate date) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredient> criteriaQuery = cb.createQuery(Ingredient.class);
        Root<Ingredient> recipeRoot = criteriaQuery.from(Ingredient.class);

        CriteriaQuery<Ingredient> query = criteriaQuery.select(recipeRoot);

        /*Subquery<Recipe> nonExpiredIngredientSubquery = query.subquery(Recipe.class);
        Root<Recipe> nonExpiredIngredient = nonExpiredIngredientSubquery.from(Recipe.class);
        nonExpiredIngredientSubquery.select(nonExpiredIngredient);

        Predicate matchingRecipe = cb.equal(nonExpiredIngredient.get("title"), recipeRoot.get("title"));
        Predicate expiredIngredient = cb.greaterThan(nonExpiredIngredient.join("ingredients").get("useBy"), date);

        Predicate allNonExpiredIngredients = cb.exists(nonExpiredIngredientSubquery.where(matchingRecipe, expiredIngredient));*/

        List<Ingredient> ingredientList =  entityManager.createQuery(query).getResultList();

        System.out.println("ingredientList :: " + ingredientList);

        ingredientList.forEach(recipe -> System.out.println("Recipe Title ::: " +recipe));

        return ingredientList;

    }

}
