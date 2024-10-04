package com.example.tastytactics.db;


import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.Plan;

import java.util.Date;
import java.util.List;

public class MealsLocalDataSourceImpl {
    private static MealsLocalDataSourceImpl repo = null;
    private LiveData<List<Meal>> storedMeals;
    private LiveData<List<Plan>> plannedMeals;
    private MealDAO mealDAO;

    private MealsLocalDataSourceImpl(Context context){
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDAO = db.getProductDAO();
        storedMeals = mealDAO.getAllMeals();
        //plannedMeals = mealDAO.getAllPlannedMeals();
    }

    public static MealsLocalDataSourceImpl getInstance(Context context){
        if(repo == null){
            repo = new MealsLocalDataSourceImpl(context);
        }
        return repo;
    }

    public LiveData<List<Meal>> getStoredMeals() {

        return storedMeals;
    }

    public LiveData<List<Plan>> getPlannedMeals(Date date) {

        plannedMeals = mealDAO.getAllPlanMeals(date);
        return plannedMeals;
    }

    public LiveData<Meal> getMealById(String id) {

        return mealDAO.getMealById(id);
    }

    public void deleteMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteMeal(meal);
            }
        }).start();
    }

    public void insertMealToFav(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertMeal(meal);
            }
        }).start();
    }

    public void insertMealToPlan(Plan meal, Date date){
        new Thread(new Runnable() {
            @Override
            public void run() {
                meal.setDate(date);
                mealDAO.insertMealToPlan(meal);
            }
        }).start();
    }

    public void deleteMealFromPlan(Plan meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteMealFromPlan(meal);
            }
        }).start();
    }
}
