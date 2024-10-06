package com.example.tastytactics.categorymeals.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.mealdetails.view.MealDetailsFragment;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealService;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.plan.view.DateFragment;
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
public class CategoryMealsFragment extends Fragment implements SearchVieww, OnSearchClickListener, OnItemClickListener {
    private MealsAddapter mealsAddapter;
    private SearchPresenter searchPresenter;
    private RecyclerView mealsRecyclerView;
    private LinearLayoutManager layoutManager;
    private String typeName;
    private String type;
    private ImageView selectedMealImage;
    private TextView mealTitle;
    private TextView typeTitle;
    private LiveData<Meal> isFav;
    private ImageButton btnAddToFav;
    private Button btnAddToPlan;
    private Meal selectedMeal;
    private boolean byId = false;

    public CategoryMealsFragment(String _typeName, String _type) {
        // Required empty public constructor
        typeName = _typeName;
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

        selectedMealImage = v.findViewById(R.id.selectedMealImage);
        mealTitle = v.findViewById(R.id.mealTitle);
        typeTitle = v.findViewById(R.id.typeTitle);
        mealsRecyclerView = v.findViewById(R.id.mealsRecyclerView);
        btnAddToFav = v.findViewById(R.id.btnAddToFav);
        btnAddToPlan = v.findViewById(R.id.btnAddToPlan);

        btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFav.getValue() == null) {
                    addToFav(selectedMeal);
                    btnAddToFav.setImageResource(R.drawable.hearttt);  // Change to heart
                } else {
                    removeFromFav(selectedMeal);
                    btnAddToFav.setImageResource(R.drawable.heartt);  // Change to like
                }
            }
        });

        btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCalenderClick(selectedMeal);
            }
        });

        selectedMealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byId = true;
                onMealClick(selectedMeal);
            }
        });


        typeTitle.setText(typeName);

        mealsRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());

        mealsAddapter = new MealsAddapter(getContext(), new ArrayList<>(), this);
        searchPresenter = new SearchPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mealsRecyclerView.setLayoutManager(layoutManager);
        mealsRecyclerView.setAdapter(mealsAddapter);
        if(type.equals("c")) {
            searchPresenter.serchByCategory(typeName);
        }
        else if(type.equals("i")) {
            searchPresenter.serchByIngredient(typeName);
        }
        else if(type.equals("a")) {
            searchPresenter.serchByCountry((typeName));
        }
        return v;
    }

    public void addToFav(Meal meal) {
        searchPresenter.addToFav(meal);
    }

    public void removeFromFav(Meal meal) {
        searchPresenter.removeFromFav(meal);
    }

    public void onCalenderClick(Meal meal) {
        DateFragment dateFragment = new DateFragment(meal);
        dateFragment.show(getChildFragmentManager(), "DateFragment");
    }

    private void updateSelectedFoodDetails(Meal meal) {
        selectedMeal = meal;
        Glide.with(this).load(meal.getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .error(R.drawable.ic_launcher_foreground))
                .circleCrop()
                .into(selectedMealImage);
        mealTitle.setText(meal.getMeal());
        isFav = searchPresenter.getFavMealById(meal.getIdMeal());
        isFav.observe(this, new Observer<Meal>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Meal meal) {
                if(meal == null) {
                    btnAddToFav.setImageResource(R.drawable.heartt);
                }
                else {
                    btnAddToFav.setImageResource(R.drawable.hearttt);
                }
            }
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        if(byId){
            navigateToMealDetails(meals.get(0));
            byId = false;
        }
        else {
            if(meals!=null) {
                mealsAddapter.setMeals(meals);
                mealsAddapter.notifyDataSetChanged();
                updateSelectedFoodDetails(meals.get(0));
            }
            else {
                Toast.makeText(getContext(), "No Meals Found", Toast.LENGTH_SHORT).show();
            }
        }

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
    public void onMealClick(Meal meal) {
        searchPresenter.getMealById(meal.getIdMeal());

    }

    @Override
    public void onItemClick(Meal meal) {
        updateSelectedFoodDetails(meal);
    }
}