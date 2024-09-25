package com.example.tastytactics.network;


import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Call<MealResponse<Meal>> getRandomMeal();

    @GET("lookup.php?i={id}")
    Call<MealResponse> getMealbyID(@Query("i") String id);

    @GET("filter.php?c={category}")
    Call<MealResponse<Meal>> getMealsbyCategory(String category);

    @GET("filter.php?a={country}")
    Call<MealResponse> getMealsbyCountry(String country);

    @GET("filter.php?i={ingredient}")
    Call<MealResponse> getMealsbyIngredient(String ingredient);

    @GET("categories.php")
    Call<MealResponse<Category>> getCategories();


}
