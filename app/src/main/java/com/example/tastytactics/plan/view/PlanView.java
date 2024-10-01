package com.example.tastytactics.plan.view;

import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.Plan;

import java.util.List;

public interface PlanView {
    public void showData(List<Plan> meals);
    public void showErrMsg(String error);
}
