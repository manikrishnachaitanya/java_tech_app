package com.rezdy.lunch.tests;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.rezdy.lunch.entity.Ingredient;
import com.rezdy.lunch.entity.Recipe;
import com.rezdy.lunch.helper.LunchServiceHelper;

@RunWith(SpringJUnit4ClassRunner.class)
class LunchServiceHelperTest
{
    /**
     * Test to verify lunch service.
     */
    @Test
    public void testLunchService_Todays_date()
    {
        // Setup data
        Ingredient ingredient = new Ingredient();
        ingredient.setTitle("Eggs");
        ingredient.setUseBy(LocalDate.MAX);
        ingredient.setBestBefore(LocalDate.MAX);
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(ingredient);

        Recipe recipe = new Recipe();
        recipe.setTitle("Omlette");
        recipe.setIngredients(ingredientSet);
        LocalDate today = LocalDate.now();

        // Actual call
        boolean isExpired = LunchServiceHelper.isExpired(recipe, today);

        // Asserts
        Assert.isTrue(!isExpired, "Recipe has expired");
    }
}