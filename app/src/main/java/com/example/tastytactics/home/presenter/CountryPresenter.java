package com.example.tastytactics.home.presenter;

import com.example.tastytactics.home.view.HomeView;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.NetworkCallback;

import java.util.List;

public class CountryPresenter implements NetworkCallback<Meal>  {
    private MealsRepositoryImpl repo;
    private HomeView view;

    public CountryPresenter(HomeView _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showCountries(meals);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }


    public void getIngredients() {
        repo.getCountries(this);
    }
}
