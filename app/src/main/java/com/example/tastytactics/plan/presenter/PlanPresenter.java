package com.example.tastytactics.plan.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.model.Plan;
import com.example.tastytactics.network.NetworkCallback;
import com.example.tastytactics.plan.view.PlanView;
import com.example.tastytactics.search.view.SearchVieww;

import java.util.Date;
import java.util.List;

public class PlanPresenter implements NetworkCallback<Plan> {
    private MealsRepositoryImpl repo;
    private PlanView view;

    public PlanPresenter(PlanView _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    @Override
    public void onSuccessResult(List<Plan> meals) {
        view.showData(meals);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }

    public LiveData<List<Plan>> getMeals()
    {
        return repo.getPlannedMeals();
    }

    public LiveData<List<Plan>> getPlannedMeals(Date date)
    {
        return repo.getPlanMeals(date);
    }

    public void addToPlan(Plan meal, Date date)
    {
        repo.insertMealToPlan(meal,date);
    }

    public void removeFromPlan(Meal meal)
    {
        repo.deleteMeal(meal);
    }
}
