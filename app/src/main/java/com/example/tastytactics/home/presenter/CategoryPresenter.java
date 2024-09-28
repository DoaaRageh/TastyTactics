package com.example.tastytactics.home.presenter;

import com.example.tastytactics.home.view.HomeView;
import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.NetworkCallback;

import java.util.List;

public class CategoryPresenter implements NetworkCallback<Category> {
    private MealsRepositoryImpl repo;
    private HomeView view;

    public CategoryPresenter(HomeView _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    @Override
    public void onSuccessResult(List<Category> categories) {
        view.showCategories(categories);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }


    public void getCategories() {
        repo.getCategories(this);
    }
}
