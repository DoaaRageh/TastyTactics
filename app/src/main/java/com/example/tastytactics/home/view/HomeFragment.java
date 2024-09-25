package com.example.tastytactics.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.home.view.HomeAdapter;
import com.example.tastytactics.home.view.HomeView;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.mealdetails.view.MealDetailsFragment;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener {

    public Button btnAddToPlan;
    public ImageView image;
    public TextView txtTitle;
    public HomeAdapter homeAdapter;
    private HomePresenter homePresenter;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
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

        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getContext());
        homeAdapter = new HomeAdapter(getContext(), new ArrayList<>(), this);
        homePresenter = new HomePresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeAdapter);

        homePresenter.getRandomMeal();

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
    public void onMealClick(String title, String instructions, String thumb) {
        navigateToMealDetails(title, instructions, thumb);
    }
}