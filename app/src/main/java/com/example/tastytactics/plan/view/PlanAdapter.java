package com.example.tastytactics.plan.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
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
        public ImageButton btnRemoveFromPlan;
        public CardView cardView;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.planMealImage);
            txtTitle = v.findViewById(R.id.favMealTitle);
            btnRemoveFromPlan = v.findViewById(R.id.btnAddRemoveFav);
            cardView = v.findViewById(R.id.planMealRow);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.plan_meal_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(meals.get(position).getMeal().getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);

        holder.txtTitle.setText(meals.get(position).getMeal().getMeal());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMealClick(meals.get(position).getMeal());
            }
        });

        holder.btnRemoveFromPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to remove this meal from plan?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // User confirmed the action, proceed with removal
                            listener.onPlannedMealClick(meals.get(position));
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

    public void setMeals(List<Plan> _meals) {
        meals = _meals;
    }
}
