package com.example.tastytactics.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.Plan;

import java.util.Date;
import java.util.List;

@Dao
public interface MealDAO {

    @Query("SELECT * FROM meal_table")
    LiveData<List<Meal>>getAllMeals();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal product);

    @Delete
    void deleteMeal(Meal product);

    @Query("SELECT * FROM meal_table WHERE idMeal = :mealId")
    LiveData<Meal> getMealById(String mealId);

    @Query("SELECT * FROM plan_table")
    LiveData<List<Plan>>getAllPlannedMeals();

    @Query("SELECT * FROM plan_table WHERE date = :planDate")
    LiveData<List<Plan>>getAllPlanMeals(Date planDate);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToPlan(Plan meal);

    @Delete
    void deleteMealFromPlan(Plan meal);
}
