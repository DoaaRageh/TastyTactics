package com.example.tastytactics.favmeals.view;


import com.example.tastytactics.model.Meal;

import java.util.List;

public interface FavMealsView {
    public void showData(List<Meal> products);
    public void showErrMsg(String error);
}
