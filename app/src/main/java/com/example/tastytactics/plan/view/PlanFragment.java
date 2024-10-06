package com.example.tastytactics.plan.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.mealdetails.view.MealDetailsFragment;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.model.Plan;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.plan.presenter.PlanPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment implements PlanView, OnPlanClickListener {
    private LiveData<List<Plan>> planList;
    private RecyclerView recyclerView;
    private PlanAdapter favAdapter;
    private PlanPresenter favPresenter;
    private GridLayoutManager gridLayoutManager;
    private CalendarView calendarView;

    public PlanFragment() {
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
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        calendarView = v.findViewById(R.id.calendarView);
        recyclerView = v.findViewById(R.id.sundayRecyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

        favPresenter = new PlanPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        recyclerView.setLayoutManager(gridLayoutManager);

        favAdapter = new PlanAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(favAdapter);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            planList = favPresenter.getPlannedMeals(calendar.getTime());

            planList.observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(List<Plan> meals) {
                    if (meals != null) {
                        showData(meals);
                    }
                    else {
                        showErrMsg("There are no meals");
                    }
                }
            });

        });

        return v;
    }

    @Override
    public void onPlannedMealClick(Plan meal) {
        favPresenter.removeFromPlan(meal);
        Toast.makeText(getContext(), "Meal Removed From Plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClick(Meal meal) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(meal);

        // Replace the current fragment with MealDetailsFragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mealDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showData(List<Plan> meals) {
        favAdapter.setMeals(meals);
        favAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
    }
}