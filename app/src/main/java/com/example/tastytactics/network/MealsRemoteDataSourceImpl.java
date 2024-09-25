package com.example.tastytactics.network;

import android.util.Log;

import com.example.tastytactics.model.Meal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private MealService mealService;
    private static MealsRemoteDataSourceImpl client = null;

    private MealsRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealsRemoteDataSourceImpl getInstance(){
        if(client == null)
        {
            client = new MealsRemoteDataSourceImpl();
        }
        return client;
    }

    public void getRandomMealFromNetwork(NetworkCallback networkCallback) {
        mealService.getRandomMeal().enqueue(new Callback<MealResponse<Meal>>() {
            @Override
            public void onResponse(Call<MealResponse<Meal>> call, Response<MealResponse<Meal>> response) {
                if(response.isSuccessful()){
                    Log.i("MainActivity", "onResponse: " + response.body().meals);
                    networkCallback.onSuccessResult(response.body().meals);
                }
            }

            @Override
            public void onFailure(Call<MealResponse<Meal>> call, Throwable throwable) {
                Log.i("MainActivity", "onFailure: ");
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }

}
