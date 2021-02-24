package com.rezdy.lunch.helper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rezdy.lunch.entity.Ingredient;
import com.rezdy.lunch.entity.Recipe;

public class LunchServiceHelper
{
    public static boolean isExpired(Recipe recipe, LocalDate date)
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
}
