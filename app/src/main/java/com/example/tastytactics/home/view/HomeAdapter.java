package com.example.tastytactics.home.view;

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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.model.Meal;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private final Context context;
    private List<Meal> meals;
    private static final String TAG = "RecyclerView";
    private OnMealClickListener listener;
    private LiveData<Meal> isFav;
    private HomePresenter homePresenter;

    // Constructor
    public HomeAdapter(Context _context, List<Meal> _meals, OnMealClickListener _listener, HomePresenter _homePresenter) {
        context = _context;
        meals = _meals;
        listener = _listener;
        homePresenter = _homePresenter;
    }

    public void setList(List<Meal> _meals)
    {
        meals = _meals;
    }


    // ViewHolder inner class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtTitle;
        public Button btnAddToPlan;
        public ImageButton btnAddToFav;
        public ConstraintLayout constraintLayout;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.mealImage);
            txtTitle = v.findViewById(R.id.mealTitle);
            btnAddToPlan = v.findViewById(R.id.btnAddToPlan);
            btnAddToFav = v.findViewById(R.id.btnAddToFav);
            constraintLayout = v.findViewById(R.id.mealRow);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.meal_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        isFav = homePresenter.getMealById(meals.get(position).getIdMeal());
        isFav.observe((LifecycleOwner) context, new Observer<Meal>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Meal meal) {
                if(meal == null) {
                    holder.btnAddToFav.setImageResource(R.drawable.heartt);
                }
                else {
                    holder.btnAddToFav.setImageResource(R.drawable.hearttt);
                }
            }
        });
        Glide.with(context).load(meals.get(position).getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);
        holder.txtTitle.setText(meals.get(position).getMeal());

        holder.btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFav.getValue() == null) {
                    listener.addToFav(meals.get(position));
                    holder.btnAddToFav.setImageResource(R.drawable.hearttt);  // Change to heart
                } else {
                    listener.removeFromFav(meals.get(position));
                    holder.btnAddToFav.setImageResource(R.drawable.heartt);  // Change to like
                }
            }
        });

        holder.btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCalenderClick(meals.get(position));
            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMealClick(meals.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {
        return meals.size();
    }
}
