package com.example.tastytactics.mealdetails.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.mealdetails.presenter.MealDetailsPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealDetailsFragment extends Fragment implements MealDetailsView{
    public TextView txtSteps;
    public TextView txtTitle;
    public ImageView image;
    private MealDetailsPresenter presenter;

    public MealDetailsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_meal_details, container, false);
        txtSteps = v.findViewById(R.id.txtSteps);
        image = v.findViewById(R.id.imageView);
        txtTitle = v.findViewById(R.id.favMealTitle);

        presenter = new MealDetailsPresenter(this);
        if (getArguments() != null) {
            String title = (String) getArguments().get("title");
            String instructions = (String) getArguments().get("instruction");
            String thumb = (String) getArguments().get("thumb");
            presenter.loadMealDetails(title, instructions, thumb);
        }
        return  v;
    }

    @Override
    public void displayMealDetails(String title, String steps, String thumb) {
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
        txtTitle.setText(title);
        Glide.with(getContext()).load(thumb)
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(image);
    }
}