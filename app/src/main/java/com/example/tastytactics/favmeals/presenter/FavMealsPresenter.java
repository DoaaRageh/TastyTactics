package com.example.tastytactics.favmeals.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastytactics.favmeals.view.FavMealsView;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;

import java.util.List;

public class FavMealsPresenter {

    private MealsRepositoryImpl repo;
    private FavMealsView view;

    public FavMealsPresenter(FavMealsView _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    public LiveData<List<Meal>> getMeals()
    {
        return repo.getStoredMeals();
    }

    public void removeFromFav(Meal meal)
    {
        repo.deleteMeal(meal);
    }

}
