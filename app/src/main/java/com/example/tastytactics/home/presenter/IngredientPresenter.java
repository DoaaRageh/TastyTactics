package com.example.tastytactics.home.presenter;

import com.example.tastytactics.home.view.HomeView;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.NetworkCallback;

import java.util.List;

public class IngredientPresenter implements NetworkCallback<Ingredient> {
    private MealsRepositoryImpl repo;
    private HomeView view;

    public IngredientPresenter(HomeView _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    @Override
    public void onSuccessResult(List<Ingredient> ingredients) {
        view.showIngredients(ingredients);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }


    public void getIngredients() {
        repo.getIngredients(this);
    }
}
