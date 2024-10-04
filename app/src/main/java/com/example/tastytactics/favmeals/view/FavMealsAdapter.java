package com.example.tastytactics.favmeals.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.model.Meal;


import java.util.List;

public class FavMealsAdapter extends RecyclerView.Adapter<FavMealsAdapter.ViewHolder>{
    private final Context context;
    private List<Meal> meals;
    private static final String TAG = "RecyclerView";
    private OnFavClickListener listener;


    public FavMealsAdapter(Context _context, List<Meal> _meals, OnFavClickListener _listener) {
        context = _context;
        meals = _meals;
        listener = _listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtTitle;
        public ImageButton btnRemoveFromFav;
        public ConstraintLayout constraintLayout;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.planMealImage);
            txtTitle = v.findViewById(R.id.favMealTitle);
            btnRemoveFromFav = v.findViewById(R.id.btnAddRemoveFav);
            constraintLayout = v.findViewById(R.id.planMealRow);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.fav_meal_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(meals.get(position).getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);
        holder.txtTitle.setText(meals.get(position).getMeal());

        Log.i(TAG, "onBindViewHolder: " + meals.get(position).getMeal());
        Log.i(TAG, "***** onBindViewHolder **************");

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMealClick(meals.get(position));
            }
        });

        holder.btnRemoveFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFavMealClick(meals.get(position));
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
