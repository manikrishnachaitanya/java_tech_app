package com.rezdy.lunch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        Subquery<Recipe> nonExpiredIngredientSubquery = query.subquery(Recipe.class);
        Root<Recipe> nonExpiredIngredient = nonExpiredIngredientSubquery.from(Recipe.class);
        nonExpiredIngredientSubquery.select(nonExpiredIngredient);

        Predicate matchingRecipe = cb.equal(nonExpiredIngredient.get("title"), recipeRoot.get("title"));
        Predicate expiredIngredient = cb.lessThan(nonExpiredIngredient.join("ingredients").get("useBy"), date);

        Predicate allNonExpiredIngredients = cb.exists(nonExpiredIngredientSubquery.where(matchingRecipe, expiredIngredient));

        List<Recipe> recipeList =  entityManager.createQuery(query.where(allNonExpiredIngredients)).getResultList();

        System.out.println("recipeList :: " + recipeList);

        recipeList.forEach(recipe -> System.out.println("Recipe Title ::: " +recipe.getTitle()));

        return recipeList;

    }

}
