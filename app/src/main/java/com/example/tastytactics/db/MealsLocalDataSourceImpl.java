package com.example.tastytactics.db;


import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.tastytactics.model.Meal;

import java.util.List;

public class MealsLocalDataSourceImpl {
    private static MealsLocalDataSourceImpl repo = null;
    private LiveData<List<Meal>> storedMeals;
    private MealDAO mealDAO;

    private MealsLocalDataSourceImpl(Context context){
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDAO = db.getProductDAO();
        storedMeals = mealDAO.getAllMeals();
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

    public void deleteMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteMeal(meal);
            }
        }).start();
    }

    public void insertMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertMeal(meal);
            }
        }).start();
    }

}
