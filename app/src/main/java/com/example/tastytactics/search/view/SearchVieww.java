package com.example.tastytactics.search.view;

import com.example.tastytactics.model.Meal;

import java.util.List;

public interface SearchVieww {
    public void showData(List<Meal> meals);
    public void showErrMsg(String error);
    void navigateToMealDetails(Meal meal);
}
