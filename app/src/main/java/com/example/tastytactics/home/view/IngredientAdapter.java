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

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private final Context context;
    private static final String TAG = "RecyclerView";
    private OnCategoryClickListener listener;
    private List<Ingredient> ingredients;
    private String imageUrl;

    // Constructor
    public IngredientAdapter(Context _context, List<Ingredient> _ingredients, OnCategoryClickListener _listener) {
        context = _context;
        ingredients = _ingredients;
        listener = _listener;
    }

    public void setCategories(List<Ingredient> _ingredients)
    {
        ingredients = _ingredients;
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
            image = v.findViewById(R.id.ingredientImage);
            txtTitle = v.findViewById(R.id.ingredientTitle);
            constraintLayout = v.findViewById(R.id.ingredientRow);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.ingredients_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredients.get(position).getIngredient() + ".png";
        Glide.with(context).load(imageUrl)
                .apply(new RequestOptions().override(200,200)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);
        holder.txtTitle.setText(ingredients.get(position).getIngredient());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIngredientClick(ingredients.get(position).getIngredient());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
