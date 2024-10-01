package com.example.tastytactics.mealdetails.view;

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

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.ViewHolder>{
    private final Context context;
    private static final String TAG = "RecyclerView";
    private OnIngredientClickListener listener;
    private List<String> ingredients;
    private List<String> measures;
    String imageUrl;

    // Constructor
    public MealDetailsAdapter(Context _context, List<String> _ingredients, List<String> _measures, OnIngredientClickListener _listener) {
        context = _context;
        ingredients = _ingredients;
        measures = _measures;
        listener = _listener;
    }


    // ViewHolder inner class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtTitle;
        public TextView txtMeasure;
        public ConstraintLayout constraintLayout;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.ingredientImage);
            txtTitle = v.findViewById(R.id.ingredientTitle);
            txtMeasure = v.findViewById(R.id.txtMeasure);
            constraintLayout = v.findViewById(R.id.ingredientRow);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MealDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.ingredient_layout, recyclerView, false);
        MealDetailsAdapter.ViewHolder vh = new MealDetailsAdapter.ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }

    @Override
    public void onBindViewHolder(MealDetailsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if(ingredients.get(position) != null && !ingredients.get(position).isEmpty()) {
            imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredients.get(position) + ".png";
            Glide.with(context).load(imageUrl)
                    .apply(new RequestOptions().override(200,200)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.image);
            holder.txtTitle.setText(ingredients.get(position));
            holder.txtMeasure.setText(measures.get(position));

            Log.i(TAG, "onBindViewHolder: " + ingredients.get(position));
            Log.i(TAG, "***** onBindViewHolder **************");



            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //listener.onIngredientClick(ingredients.get(position).getCategory());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}

