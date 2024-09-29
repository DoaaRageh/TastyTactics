package com.example.tastytactics.mealdetails.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.presenter.CategoryPresenter;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.home.view.CategoryAdapter;
import com.example.tastytactics.home.view.HomeAdapter;
import com.example.tastytactics.mealdetails.presenter.MealDetailsPresenter;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealDetailsFragment extends Fragment implements MealDetailsView, OnIngredientClickListener{
    @Override
    public void onIngredientClick(Ingredient ingredient) {

    }

    public TextView txtSteps;
    public TextView txtTitle;
    public ImageView image;
    public ImageButton btnAddToFav;
    private WebView webView;
    private MealDetailsPresenter presenter;
    public MealDetailsAdapter mealDetailsAdapter;
    private RecyclerView recyclerView;
    //GridLayoutManager gridLayoutManager;
    LinearLayoutManager layoutManager;

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
        webView = v.findViewById(R.id.webView);

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


        //gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mealDetailsAdapter = new MealDetailsAdapter(getContext(), meal.getIngredients(), meal.getMeasures(), this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealDetailsAdapter);

        //presenter.getIngredients();

        return  v;
    }

    @Override
    public void displayMealDetails(Meal meal) {
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
        Glide.with(getContext()).load(meal.getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(image);
        btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFavClick(meal);
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
    public void onFavClick(Meal meal) {
        presenter.addToFav(meal);
        Toast.makeText(getContext(),"Meal Added To Favorite",Toast.LENGTH_SHORT).show();
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