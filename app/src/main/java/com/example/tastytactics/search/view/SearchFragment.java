package com.example.tastytactics.search.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.mealdetails.view.MealDetailsFragment;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.search.presenter.SearchPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchVieww, OnSearchClickListener {
    private SearchAdapter searchAdapter;
    private SearchPresenter searchPresenter;
    private RecyclerView searchRecyclerView;
    LinearLayoutManager layoutManager;

    int position = 0;
    public SearchFragment() {
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
        View v = inflater.inflate(R.layout.fragment_search, container, false);




        searchRecyclerView = v.findViewById(R.id.searchRecyclerView);
        TabLayout tabLayout = v.findViewById(R.id.tabLayout);
        SearchView searchView = v.findViewById(R.id.searchVieww);

        searchRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());

        searchAdapter = new SearchAdapter(getContext(), new ArrayList<>(), this);
        searchPresenter = new SearchPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(searchAdapter);

        /*btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchPresenter.serchByCategory(editSearch.getText().toString());
            }
        });*/


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Get the selected tab position
                position = tab.getPosition();

                // Perform different actions based on the selected tab
                switch (position) {
                    case 0:
                        searchView.setQueryHint("Enter Meal Name");
                        break;
                    case 1:
                        searchView.setQueryHint("Enter Country Name");
                        break;
                    case 2:
                        searchView.setQueryHint("Enter Ingredient Name");
                        break;
                    case 3:
                        searchView.setQueryHint("Enter Category Name");
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                switch (position) {
                    case 0:
                        searchPresenter.serchByMealName(searchView.getQuery().toString());
                        break;
                    case 1:
                        searchPresenter.serchByCountry(searchView.getQuery().toString());
                        break;
                    case 2:
                        searchPresenter.serchByIngredient(searchView.getQuery().toString());
                        break;
                    case 3:
                        searchPresenter.serchByCategory(searchView.getQuery().toString());
                        break;
                }
                //Toast.makeText(getContext(), "Search query: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text change if needed
                return false;
            }
        });

        return v;
    }

    @Override
    public void showData(List<Meal> meals) {
        if(searchAdapter.byId){
            navigateToMealDetails(meals.get(0));
            Log.i("byId", "showData: ");
            searchAdapter.byId = false;
        }
        else {
            searchAdapter.setMeals(meals);
            searchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClick(Meal meal) {
        searchPresenter.getMealById(meal.getIdMeal());
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
}