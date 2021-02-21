package com.rezdy.lunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.rezdy.lunch.entity.Recipe;

public interface LunchRepository extends JpaRepository<Recipe, String>
{
}
