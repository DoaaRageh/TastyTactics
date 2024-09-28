package com.example.tastytactics.network;


import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Call<MealResponse<Meal>> getRandomMeal();

    @GET("lookup.php")
    Call<MealResponse<Meal>> getMealbyID(@Query("i") String id);

    @GET("search.php")
    Call<MealResponse<Meal>> getMealsbyName(@Query("s") String mealName);

    @GET("filter.php")
    Call<MealResponse<Meal>> getMealsbyCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse<Meal>> getMealsbyCountry(@Query("a") String country);

    @GET("filter.php")
    Call<MealResponse<Meal>> getMealsbyIngredient(@Query("i") String ingredient);

    @GET("categories.php")
    Call<CategoryResponse<Category>> getCategories();

    @GET("list.php?i=list")
    Call<MealResponse<Ingredient>> getIngredients();

    /*@GET("list.php?c=list")
    Call<MealResponse<Category>> getCategoriess();*/


}
