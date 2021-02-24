package com.rezdy.lunch.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rezdy.lunch.entity.Recipe;
import com.rezdy.lunch.helper.LunchServiceHelper;

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

    public List<Recipe> loadRecipes(LocalDate date)
    {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        List<Recipe> recipeList = null;
        if (cb != null)
        {
            CriteriaQuery<Recipe> criteriaQuery = cb.createQuery(Recipe.class);
            Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);
            CriteriaQuery<Recipe> query = criteriaQuery.select(recipeRoot);
            recipeList = entityManager.createQuery(query).getResultList();
        }

        System.out.println("recipeList :: " + recipeList);

        recipeList.forEach(recipe -> System.out.println("Recipe Title ::: " + recipe));

        List<Recipe> validRecipes = recipeList.stream().filter(recipe -> !LunchServiceHelper.isExpired(recipe, date))
                .collect(Collectors.toList());
        return validRecipes;

    }
}
