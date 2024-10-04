package com.example.tastytactics.home.view;


import com.example.tastytactics.model.Meal;

public interface OnMealClickListener {
    void onMealClick(Meal meal);
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
    void onCalenderClick(Meal meal);
}
