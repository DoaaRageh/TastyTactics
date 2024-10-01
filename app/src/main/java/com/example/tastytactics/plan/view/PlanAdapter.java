package com.example.tastytactics.plan.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.favmeals.view.OnFavClickListener;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.model.Plan;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder>{
    private final Context context;
    private List<Plan> meals;
    private static final String TAG = "RecyclerView";
    private OnPlanClickListener listener;


    public PlanAdapter(Context _context, List<Plan> _meals, OnPlanClickListener _listener) {
        context = _context;
        meals = _meals;
        listener = _listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtTitle;
        public TextView txtDate;
        public Button btnRemoveFromFav;
        public ConstraintLayout constraintLayout;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.planMealImage);
            txtTitle = v.findViewById(R.id.txtPlanTitle);
            txtDate = v.findViewById(R.id.txtDate);
            btnRemoveFromFav = v.findViewById(R.id.btnAddRemoveFav);
            constraintLayout = v.findViewById(R.id.planMealRow);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.plan_meal_layout, recyclerView, false);
        PlanAdapter.ViewHolder vh = new PlanAdapter.ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(meals.get(position).getMeal().getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);
        //holder.txtTitle.setText(meals.get(position).getMeal());
        holder.txtTitle.setText(meals.get(position).getMeal().getMeal());

        Log.i(TAG, "onBindViewHolder: " + meals.get(position).getMeal());
        Log.i(TAG, "***** onBindViewHolder **************");





    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<Plan> _meals) {
        meals = _meals;
    }
}
