package com.example.tastytactics.search.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.NetworkCallback;
import com.example.tastytactics.search.view.SearchVieww;

import java.util.List;

public class SearchPresenter implements NetworkCallback<Meal> {
    private MealsRepositoryImpl repo;
    private SearchVieww view;

    public SearchPresenter(SearchVieww _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showData(meals);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }

    public void serchByCountry(String country) {
        repo.getMealsByCountry(this, country);
    }

    public LiveData<Meal> getFavMealById(String id)
    {
        return repo.getMealById(id);
    }

    public void serchByCategory(String category) {
        repo.getMealsByCategory(this, category);
    }

    public void serchByIngredient(String ingredient) {
        repo.getMealsByIngredient(this, ingredient);
    }

    public void serchByMealName(String mealName) {
        repo.getMealsByName(this, mealName);
    }

    public void getMealById(String id) {
        repo.getMealsById(this, id);
    }

    public void addToFav(Meal meal)
    {
        repo.insertMeal(meal);
    }

    public void removeFromFav(Meal meal)
    {
        repo.deleteMeal(meal);
    }
}
