package com.example.tastytactics.home.view;

import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Meal;

import java.util.List;

public interface HomeView {
    public void showData(List<Meal> meals);
    public void showErrMsg(String error);
    public void showCategories(List<Category> Category);
    void navigateToMealDetails(String title, String instructions, String thumb);
}
