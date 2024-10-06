package com.example.tastytactics.mealdetails.view;

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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.categorymeals.view.CategoryMealsFragment;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.mealdetails.presenter.MealDetailsPresenter;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.plan.view.DateFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealDetailsFragment extends Fragment implements MealDetailsView, OnMealClickListener, OnIngredientClickListener{

    public TextView txtSteps;
    public TextView txtTitle;
    public TextView txtCountry;
    public ImageView image;
    public ImageButton btnAddToFav;
    public Button btnAddToPlan;
    private WebView webView;
    private MealDetailsPresenter presenter;
    public MealDetailsAdapter mealDetailsAdapter;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private LiveData<Meal> isFav;

    Meal meal;

    public MealDetailsFragment(Meal _meal) {
        meal=_meal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meal_details, container, false);
        txtSteps = v.findViewById(R.id.txtSteps);
        image = v.findViewById(R.id.imageView);
        txtTitle = v.findViewById(R.id.mealTitle);
        txtCountry = v.findViewById(R.id.txtCountry);
        webView = v.findViewById(R.id.webView);
        btnAddToPlan = v.findViewById(R.id.btnAddToPlan);

        btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCalenderClick(meal);
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        btnAddToFav = v.findViewById(R.id.btnAddToFav);
        presenter = new MealDetailsPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));

        if(meal != null) {
            presenter.loadMealDetails(meal);
        }

        recyclerView = v.findViewById(R.id.ingredientsRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutFrozen(true);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mealDetailsAdapter = new MealDetailsAdapter(getContext(), meal.getIngredients(), meal.getMeasures(), this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealDetailsAdapter);

        return  v;
    }

    @Override
    public void displayMealDetails(Meal meal) {
        isFav = presenter.getMealById(meal.getIdMeal());
        isFav.observe(getViewLifecycleOwner(), new Observer<Meal>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Meal _meal) {
                if(_meal == null) {
                    btnAddToFav.setImageResource(R.drawable.heartone);
                }
                else {
                    btnAddToFav.setImageResource(R.drawable.hearttwo);
                }
            }
        });
        String steps = meal.getInstructions();
        String[] parts = steps.split("\\.");

        StringBuilder modifiedText = new StringBuilder();
        for (String part : parts) {
            part = part.trim();
            if (!part.endsWith("!")) {
                modifiedText.append("- ").append(part).append(".\n\n");
            }
            else{
                modifiedText.append(part).append("\n\n");
            }
        }
        txtSteps.setText(modifiedText);
        txtTitle.setText(meal.getMeal());
        txtCountry.setText(meal.getArea()
        );
        Glide.with(getContext()).load(meal.getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(image);
        btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFav.getValue() == null) {
                    addToFav(meal);
                    btnAddToFav.setImageResource(R.drawable.hearttwo);  // Change to heart
                } else {
                    removeFromFav(meal);
                    btnAddToFav.setImageResource(R.drawable.heartone);  // Change to like
                }
            }
        });

        String youtubeUrl = meal.getYoutubeURL();
        String videoId = getYoutubeVideoId(youtubeUrl);

        if (videoId != null) {
            String youtubeEmbedUrl = "https://www.youtube.com/embed/" + videoId;
            webView.loadUrl(youtubeEmbedUrl);
        } else {
            Toast.makeText(getContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClick(Meal meal) {
    }

    @Override
    public void addToFav(Meal meal) {
        presenter.addToFav(meal);
    }

    @Override
    public void removeFromFav(Meal meal) {
        presenter.removeFromFav(meal);
    }

    @Override
    public void onCalenderClick(Meal meal) {
        DateFragment dateFragment = new DateFragment(meal);
        dateFragment.show(getChildFragmentManager(), "DateFragment");

    }

    @Override
    public void onIngredientClick(String ingredientName) {
        CategoryMealsFragment categoryMealsFragment = new CategoryMealsFragment(ingredientName, "i");

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, categoryMealsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String getYoutubeVideoId(String youtubeUrl) {
        String videoId = "";
        if(youtubeUrl != null && youtubeUrl.contains("v=")) {
            String[] parts = youtubeUrl.split("v=");
            videoId = parts[1].split("&")[0];
        }
        return videoId;
    }
}