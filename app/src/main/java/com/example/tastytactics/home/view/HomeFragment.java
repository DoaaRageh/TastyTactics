package com.example.tastytactics.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.presenter.CategoryPresenter;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.mealdetails.view.MealDetailsFragment;
import com.example.tastytactics.model.Category;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener, OnCategoryClickListener {

    public HomeAdapter homeAdapter;
    public CategoryAdapter categoryAdapter;
    private CategoryPresenter categoryPresenter;
    private HomePresenter homePresenter;
    private RecyclerView recyclerView;
    private RecyclerView categoriesrecyclerView;
    LinearLayoutManager layoutManager;
    LinearLayoutManager categoryLayoutManager;

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

        recyclerView.setHasFixedSize(true);
        categoriesrecyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getContext());
        categoryLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        homeAdapter = new HomeAdapter(getContext(), new ArrayList<>(), this);
        homePresenter = new HomePresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeAdapter);

        homePresenter.getRandomMeal();
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>(), this);
        categoryPresenter = new CategoryPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);


        categoriesrecyclerView.setLayoutManager(categoryLayoutManager);
        categoriesrecyclerView.setAdapter(categoryAdapter);

        homePresenter.getRandomMeal();
        categoryPresenter.getCategories();

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
    public void navigateToMealDetails(String title, String instructions, String thumb) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("instruction", instructions);
        bundle.putString("thumb", thumb);
        mealDetailsFragment.setArguments(bundle);

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
    public void onMealClick(String title, String instructions, String thumb) {
        navigateToMealDetails(title, instructions, thumb);
    }

    @Override
    public void onFavClick(Meal meal) {
        homePresenter.addToFav(meal);
        Toast.makeText(getContext(),"Product Added To Favourite",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCategoryClick(String categoryName) {

    }
}