package com.example.tastytactics.home.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastytactics.home.view.HomeView;
import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.NetworkCallback;

import java.util.List;

public class HomePresenter implements NetworkCallback<Meal> {
    private MealsRepositoryImpl repo;
    private HomeView view;

    public HomePresenter(HomeView _view, MealsRepositoryImpl _repo) {
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


    public void getRandomMeal() {
        repo.getRandomMeal(this);
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

    public void getCategories() {
        repo.getCategories(new NetworkCallback<Category>() {
            @Override
            public void onSuccessResult(List<Category> categories) {
                view.showCategories(categories);
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showErrMsg(errorMsg);
            }
        });
    }

    // Handle Ingredients
    public void getIngredients() {
        repo.getIngredients(new NetworkCallback<Ingredient>() {
            @Override
            public void onSuccessResult(List<Ingredient> ingredients) {
                view.showIngredients(ingredients);
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showErrMsg(errorMsg);
            }
        });
    }

    // Handle Countries (if you want to treat countries as meals)
    public void getCountries() {
        repo.getCountries(new NetworkCallback<Meal>() {
            @Override
            public void onSuccessResult(List<Meal> meals) {
                view.showCountries(meals);
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showErrMsg(errorMsg);
            }
        });
    }

}
