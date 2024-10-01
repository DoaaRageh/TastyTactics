package com.example.tastytactics.home.presenter;

import com.example.tastytactics.home.view.HomeView;
import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.model.Plan;
import com.example.tastytactics.network.NetworkCallback;

import java.util.Date;
import java.util.List;

public class HomePresenter implements NetworkCallback<Meal>{
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

    public void addToPlan(Plan meal, Date date)
    {
        repo.insertMealToPlan(meal,date);
    }
}
