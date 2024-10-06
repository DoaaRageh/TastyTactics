package com.example.tastytactics.home.view;

import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;

import java.util.List;

public interface HomeView {
    public void showData(List<Meal> meals);
    public void showErrMsg(String error);
    public void showCategories(List<Category> Category);
    public void showIngredients(List<Ingredient> ingredients);
    public void showCountries(List<Meal> meals);
}
