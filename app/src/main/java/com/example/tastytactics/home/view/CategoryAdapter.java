package com.example.tastytactics.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.tastytactics.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private final Context context;
    private static final String TAG = "RecyclerView";
    private OnCategoryClickListener listener;
    private List<Category> categories;

    // Constructor
    public CategoryAdapter(Context _context, List<Category> _categories, OnCategoryClickListener _listener) {
        context = _context;
        categories = _categories;
        listener = _listener;
    }

    public void setCategories(List<Category> _categories)
    {
        categories = _categories;
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
            image = v.findViewById(R.id.categoryImage);
            txtTitle = v.findViewById(R.id.categoryTitle);
            constraintLayout = v.findViewById(R.id.categoryRow);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.category_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(categories.get(position).getCategoryThumb())
                .apply(new RequestOptions().override(200,200)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);
        holder.txtTitle.setText(categories.get(position).getCategory());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoryClick(categories.get(position).getCategory());
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
