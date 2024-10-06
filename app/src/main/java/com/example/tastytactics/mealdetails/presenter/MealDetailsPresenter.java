package com.example.tastytactics.mealdetails.presenter;


import androidx.lifecycle.LiveData;

import com.example.tastytactics.mealdetails.view.MealDetailsView;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;


public class MealDetailsPresenter{
    private MealDetailsView view;
    private MealsRepositoryImpl repo;

    public MealDetailsPresenter(MealDetailsView _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    public void loadMealDetails(Meal meal) {
        view.displayMealDetails(meal);
    }

    public void addToFav(Meal meal)
    {
        repo.insertMeal(meal);
    }

    public void removeFromFav(Meal meal)
    {
        repo.deleteMeal(meal);
    }

    public LiveData<Meal> getMealById(String id)
    {
        return repo.getMealById(id);
    }
}
