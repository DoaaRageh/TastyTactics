package com.example.tastytactics.mealdetails.presenter;


import com.example.tastytactics.mealdetails.view.MealDetailsView;

public class MealDetailsPresenter  {
    private MealDetailsView view;

    public MealDetailsPresenter(MealDetailsView _view) {
        view = _view;
    }

    public void loadMealDetails(String title, String instructions, String thumb) {
        view.displayMealDetails(title, instructions, thumb);
    }
}
