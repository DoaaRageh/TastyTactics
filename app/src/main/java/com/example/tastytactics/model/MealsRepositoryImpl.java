package com.example.tastytactics.model;

import androidx.lifecycle.LiveData;

import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.network.NetworkCallback;

import java.util.Date;
import java.util.List;

public class MealsRepositoryImpl {
    MealsRemoteDataSourceImpl remoteSource;
    MealsLocalDataSourceImpl localSource;
    private static MealsRepositoryImpl repo = null;

    public MealsRepositoryImpl(MealsRemoteDataSourceImpl _remoteSource, MealsLocalDataSourceImpl _localSource)
    {
        remoteSource = _remoteSource;
        localSource = _localSource;
    }

    public static MealsRepositoryImpl getInstance(MealsRemoteDataSourceImpl _remoteSource, MealsLocalDataSourceImpl _localSource)
    {
        if(repo == null)
        {
            repo = new MealsRepositoryImpl(_remoteSource, _localSource);
        }
        return repo;
    }

    public LiveData<List<Meal>> getStoredMeals()
    {
        return localSource.getStoredMeals();
    }

    public LiveData<List<Plan>> getPlannedMeals()
    {
        return localSource.getPlannedMeals();
    }

    public LiveData<List<Plan>> getPlanMeals(Date date)
    {
        return localSource.getPlanMeals(date);
    }
    public void getRandomMeal(NetworkCallback networkCallback)
    {
        remoteSource.getRandomMealFromNetwork(networkCallback);
    }

    public void getCategories(NetworkCallback networkCallback)
    {
        remoteSource.getCategoriesFromNetwork(networkCallback);
    }

    public void getIngredients(NetworkCallback networkCallback)
    {
        remoteSource.getIngredientsFromNetwork(networkCallback);
    }

    public void getMealsByCategory(NetworkCallback networkCallback, String category)
    {
        remoteSource.getMealsByCategoryFromNetwork(networkCallback, category);
    }

    public void getMealsByCountry(NetworkCallback networkCallback, String country)
    {
        remoteSource.getMealsByCountryFromNetwork(networkCallback, country);
    }

    public void getMealsByIngredient(NetworkCallback networkCallback, String ingredient)
    {
        remoteSource.getMealsByIngredientFromNetwork(networkCallback, ingredient);
    }

    public void getMealsByName(NetworkCallback networkCallback, String mealName)
    {
        remoteSource.getMealsByNameFromNetwork(networkCallback, mealName);
    }

    public void getMealsById(NetworkCallback networkCallback, String id)
    {
        remoteSource.getMealByIdFromNetwork(networkCallback, id);
    }

    public void insertMeal(Meal meal)
    {
        localSource.insertMealToFav(meal);
    }

    public void insertMealToPlan(Plan meal, Date date)
    {
        localSource.insertMealToPlan(meal, date);
    }

    public void deleteMeal(Meal meal)
    {
        localSource.deleteMeal(meal);
    }

    public void deleteMealFromPlan(Plan meal)
    {
        localSource.deleteMealFromPlan(meal);
    }


}
