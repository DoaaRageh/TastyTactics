package com.example.tastytactics.mealdetails.view;

import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;

public interface OnIngredientClickListener {
    void onIngredientClick(String ingredient);
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
    void onCalenderClick(Meal meal);
}
