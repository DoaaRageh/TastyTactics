package com.example.tastytactics.categorymeals.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.search.view.OnSearchClickListener;

import java.util.List;

public class MealsAddapter extends RecyclerView.Adapter<MealsAddapter.ViewHolder>{
    private final Context context;
    private static final String TAG = "RecyclerView";
    private OnItemClickListener listener;
    private List<Meal> meals;
    public  boolean byId=false;

    // Constructor
    public MealsAddapter(Context _context, List<Meal> _meals, OnItemClickListener _listener) {
        context = _context;
        meals = _meals;
        listener = _listener;
    }

    public void setMeals(List<Meal> _meals)
    {
        meals = _meals;
    }

    // ViewHolder inner class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtTitle;
        public ConstraintLayout constraintLayout;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.mealImage);
            //txtTitle = v.findViewById(R.id.mealTitle);
            constraintLayout = v.findViewById(R.id.mealRow);

        }

        public void bind(final Meal meal, final OnItemClickListener listener) {
            Glide.with(context).load(meal.getMealThumb())
                    .apply(new RequestOptions().override(200,200)
                            .error(R.drawable.ic_launcher_foreground))
                    .circleCrop()
                    .into(image);
            //txtTitle.setText(meal.getMeal());
            itemView.setOnClickListener(v -> listener.onItemClick(meal));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.meals_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(meals.get(position).getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .error(R.drawable.ic_launcher_foreground))
                .circleCrop()
                .into(holder.image);
        //holder.txtTitle.setText(meals.get(position).getMeal());
        Log.i(TAG, "onBindViewHolder: " + meals.get(position).getMeal());
        Log.i(TAG, "***** onBindViewHolder **************");



        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byId=true;
                listener.onItemClick(meals.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
