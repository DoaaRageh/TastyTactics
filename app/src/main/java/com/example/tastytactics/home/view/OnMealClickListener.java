package com.example.tastytactics.home.view;


import com.example.tastytactics.model.Meal;

public interface OnMealClickListener {
    void onMealClick(String title, String instructions, String thumb);
    void onFavClick(Meal meal);
}
