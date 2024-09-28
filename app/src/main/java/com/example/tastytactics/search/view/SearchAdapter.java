package com.example.tastytactics.search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.model.Meal;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private final Context context;
    private static final String TAG = "RecyclerView";
    private OnSearchClickListener listener;
    private List<Meal> meals;

    // Constructor
    public SearchAdapter(Context _context, List<Meal> _meals, OnSearchClickListener _listener) {
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
            image = v.findViewById(R.id.searchImage);
            txtTitle = v.findViewById(R.id.searchTitle);
            constraintLayout = v.findViewById(R.id.searchRow);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.search_layout, recyclerView, false);
        SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(meals.get(position).getMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .circleCrop()
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

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
