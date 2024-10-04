package com.example.tastytactics.categorymeals.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.search.presenter.SearchPresenter;
import com.example.tastytactics.search.view.OnSearchClickListener;
import com.example.tastytactics.search.view.SearchAdapter;
import com.example.tastytactics.search.view.SearchVieww;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryMealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryMealsFragment extends Fragment implements SearchVieww, OnSearchClickListener {
    private SearchAdapter searchAdapter;
    private SearchPresenter searchPresenter;
    private RecyclerView mealsRecyclerView;
    LinearLayoutManager layoutManager;
    String categoryName;
    String type;

    public CategoryMealsFragment(String _categoryName, String _type) {
        // Required empty public constructor
        categoryName = _categoryName;
        type = _type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category_meals, container, false);

        mealsRecyclerView = v.findViewById(R.id.mealsRecyclerView);

        mealsRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());

        searchAdapter = new SearchAdapter(getContext(), new ArrayList<>(), this);
        searchPresenter = new SearchPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mealsRecyclerView.setLayoutManager(layoutManager);
        mealsRecyclerView.setAdapter(searchAdapter);
        if(type.equals("c")) {
            searchPresenter.serchByCategory(categoryName);
        }
        else if(type.equals("i")) {
            searchPresenter.serchByIngredient(categoryName);
        }
        else if(type.equals("a")) {
            searchPresenter.serchByCountry((categoryName));
        }
        return v;
    }

    @Override
    public void showData(List<Meal> meals) {

        if(meals!=null) {
            searchAdapter.setMeals(meals);
            searchAdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(getContext(), "No Meals Found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMealDetails(Meal meal) {

    }

    @Override
    public void onMealClick(Meal meal) {

    }
}