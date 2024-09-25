package com.example.tastytactics.model;

import androidx.lifecycle.LiveData;

import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.network.NetworkCallback;

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

    public void getRandomMeal(NetworkCallback networkCallback)
    {
        remoteSource.getRandomMealFromNetwork(networkCallback);
    }

    public void insertMeal(Meal meal)
    {
        localSource.insertMeal(meal);
    }

    public void deleteProduct(Meal meal)
    {
        localSource.deleteMeal(meal);
    }
}
