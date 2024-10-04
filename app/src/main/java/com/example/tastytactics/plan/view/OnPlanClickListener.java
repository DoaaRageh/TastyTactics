package com.example.tastytactics.plan.view;

import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.Plan;

public interface OnPlanClickListener {
    public void onPlannedMealClick(Plan meal);
    void onMealClick(Meal meal);
}
