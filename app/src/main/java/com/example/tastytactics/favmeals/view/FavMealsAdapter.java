package com.example.tastytactics.favmeals.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.model.Meal;


import java.util.List;

public class FavMealsAdapter extends RecyclerView.Adapter<FavMealsAdapter.ViewHolder>{
    private final Context context;
    private List<Meal> meals;
    private static final String TAG = "RecyclerView";
    private OnMealClickListener listener;


    public FavMealsAdapter(Context _context, List<Meal> _meals, OnMealClickListener _listener) {
        context = _context;
        meals = _meals;
        listener = _listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtTitle;
        public ImageButton btnRemoveFromFav;
        public CardView cardView;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.planMealImage);
            txtTitle = v.findViewById(R.id.favMealTitle);
            btnRemoveFromFav = v.findViewById(R.id.btnAddRemoveFav);
            cardView = v.findViewById(R.id.planMealRow);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.fav_meal_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(meals.get(position).getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);
        holder.txtTitle.setText(meals.get(position).getMeal());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMealClick(meals.get(position));
            }
        });

        holder.btnRemoveFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to remove this meal from favorites?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // User confirmed the action, proceed with removal
                            listener.removeFromFav(meals.get(position));
                        })
                        .setNegativeButton(android.R.string.no, null) // No action on cancel
                        .show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<Meal> _meals) {
        meals = _meals;
    }
}
