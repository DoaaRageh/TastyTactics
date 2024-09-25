package com.example.tastytactics.home.view;

import com.example.tastytactics.model.Meal;

import java.util.List;

public interface HomeView {
    public void showData(List<Meal> meals);
    public void showErrMsg(String error);
    void navigateToMealDetails(String title, String instructions, String thumb);
}
