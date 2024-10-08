package com.example.tastytactics.mealdetails.view;

import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;

import java.util.List;

public interface MealDetailsView {
    void displayMealDetails(Meal meal);
    public void showErrMsg(String error);
}
