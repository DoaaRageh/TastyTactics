package com.example.tastytactics.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.categorymeals.view.CategoryMealsFragment;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.presenter.CategoryPresenter;
import com.example.tastytactics.home.presenter.CountryPresenter;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.home.presenter.IngredientPresenter;
import com.example.tastytactics.mealdetails.view.MealDetailsFragment;
import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.plan.view.DateFragment;

import java.util.ArrayList;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener, OnCategoryClickListener {

    public HomeAdapter homeAdapter;
    public CategoryAdapter categoryAdapter;
    public IngredientAdapter ingredientAdapter;
    private CategoryPresenter categoryPresenter;
    public CountryAdapter countryAdapter;
    private CountryPresenter countryPresenter;
    private IngredientPresenter ingredientPresenter;
    private HomePresenter homePresenter;
    private RecyclerView recyclerView;
    private RecyclerView categoriesrecyclerView;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView countriesRecyclerView;
    LinearLayoutManager layoutManager;
    LinearLayoutManager categoryLayoutManager;
    LinearLayoutManager ingredientLayoutManager;
    LinearLayoutManager countriesLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        categoriesrecyclerView = v.findViewById(R.id.categoryRecyclerView);
        ingredientsRecyclerView = v.findViewById(R.id.ingredientsRecyclerView);
        countriesRecyclerView = v.findViewById(R.id.countryRecyclerView);

        recyclerView.setHasFixedSize(true);
        categoriesrecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setHasFixedSize(true);
        countriesRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        categoryLayoutManager = new LinearLayoutManager(getContext());
        ingredientLayoutManager = new LinearLayoutManager(getContext());
        countriesLayoutManager = new LinearLayoutManager(getContext());

        homePresenter = new HomePresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        homeAdapter = new HomeAdapter(getContext(), new ArrayList<>(), this, homePresenter);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeAdapter);

        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>(), this);
        categoryPresenter = new CategoryPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        ingredientPresenter = new IngredientPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        ingredientAdapter = new IngredientAdapter(getContext(), new ArrayList<>(), this);
        ingredientLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        countryAdapter = new CountryAdapter(getContext(), new ArrayList<>(), this);
        countryPresenter = new CountryPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        countriesLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        countriesRecyclerView.setLayoutManager(countriesLayoutManager);
        countriesRecyclerView.setAdapter(countryAdapter);

        categoriesrecyclerView.setLayoutManager(categoryLayoutManager);
        categoriesrecyclerView.setAdapter(categoryAdapter);

        ingredientsRecyclerView.setLayoutManager(ingredientLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);

        homePresenter.getRandomMeal();
        categoryPresenter.getCategories();
        ingredientPresenter.getIngredients();
        countryPresenter.getIngredients();

        return v;
    }

    @Override
    public void showData(List<Meal> meals) {
        homeAdapter.setList(meals);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void navigateToMealDetails(Meal meal) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(meal);

        // Replace the current fragment with MealDetailsFragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mealDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        ingredientAdapter.setCategories(ingredients);
        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCountries(List<Meal> ingredients) {
        countryAdapter.setCategories(ingredients);
        countryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMealClick(Meal meal) {
        navigateToMealDetails(meal);
    }

    @Override
    public void addToFav(Meal meal) {
        homePresenter.addToFav(meal);
        //Toast.makeText(getContext(),"Meal Added To Favorite",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeFromFav(Meal meal) {
        homePresenter.removeFromFav(meal);
        //Toast.makeText(getContext(),"Meal Removed From Favorite",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalenderClick(Meal meal) {
        DateFragment dateFragment = new DateFragment(meal);
        dateFragment.show(getChildFragmentManager(), "DateFragment");
    }

    @Override
    public void onCategoryClick(String categoryName) {
        CategoryMealsFragment categoryMealsFragment = new CategoryMealsFragment(categoryName, "c");

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, categoryMealsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onIngredientClick(String ingredientName) {
        CategoryMealsFragment categoryMealsFragment = new CategoryMealsFragment(ingredientName, "i");

        // Replace the current fragment with MealDetailsFragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, categoryMealsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCountryClick(String categoryName) {
        CategoryMealsFragment categoryMealsFragment = new CategoryMealsFragment(categoryName, "a");

        // Replace the current fragment with MealDetailsFragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, categoryMealsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}