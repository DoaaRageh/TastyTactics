package com.example.tastytactics.favmeals.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.favmeals.presenter.FavMealsPresenter;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.mealdetails.view.MealDetailsFragment;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavMealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavMealsFragment extends Fragment implements FavMealsView, OnMealClickListener {
    private LiveData<List<Meal>> mealsList;
    private RecyclerView recyclerView;
    private FavMealsAdapter favAdapter;
    FavMealsPresenter favPresenter;
    LinearLayoutManager layoutManager;

    public FavMealsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_fav_meals, container, false);
        recyclerView = v.findViewById(R.id.favMealsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        favPresenter = new FavMealsPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        favAdapter = new FavMealsAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(favAdapter);

        mealsList = favPresenter.getMeals();

        mealsList.observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals != null) {
                    showData(meals);
                }
                else {
                    showErrMsg("There are no meals");
                }
            }
        });

        return v;
    }

    @Override
    public void onMealClick(Meal meal) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(meal);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mealDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void addToFav(Meal meal) {
    }

    @Override
    public void removeFromFav(Meal meal) {
        favPresenter.removeFromFav(meal);
        Toast.makeText(getContext(), "Meal Removed From Favorite", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalenderClick(Meal meal) {
    }

    @Override
    public void showData(List<Meal> meals) {
        favAdapter.setMeals(meals);
        favAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
    }
}