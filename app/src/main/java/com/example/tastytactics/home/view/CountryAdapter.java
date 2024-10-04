package com.example.tastytactics.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastytactics.R;
import com.example.tastytactics.model.Ingredient;
import com.example.tastytactics.model.Meal;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{
    private final Context context;
    private static final String TAG = "RecyclerView";
    private OnCategoryClickListener listener;
    private List<Meal> countries;
    private String imageUrl;
    int id;

    // Constructor
    public CountryAdapter(Context _context, List<Meal> _ingredients, OnCategoryClickListener _listener) {
        context = _context;
        countries = _ingredients;
        listener = _listener;
    }

    public void setCategories(List<Meal> _ingredients)
    {
        countries = _ingredients;
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
            image = v.findViewById(R.id.countryimage);
            constraintLayout = v.findViewById(R.id.countryRow);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.country_layout, recyclerView, false);
        CountryAdapter.ViewHolder vh = new CountryAdapter.ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        id = context.getResources().getIdentifier(countries.get(position).getArea().toLowerCase(), "drawable", context.getPackageName());
        holder.image.setImageResource(id);

        Log.i(TAG, "onBindViewHolder: " + countries.get(position).getArea());
        Log.i(TAG, "***** onBindViewHolder **************");



        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCountryClick(countries.get(position).getArea());
            }
        });

    }

    @Override
    public int getItemCount() {
        return countries .size();
    }
}
