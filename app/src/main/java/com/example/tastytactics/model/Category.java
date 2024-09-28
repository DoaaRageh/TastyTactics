package com.example.tastytactics.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class Category {
    @PrimaryKey
    @NonNull
    @SerializedName("idCategory")
    private String id;
    @SerializedName("strCategory")
    private String category;
    @SerializedName("strCategoryThumb")
    private String categoryThumb;
    @SerializedName("strCategoryDescription")
    private String description;

    @NonNull
    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryThumb(String categoryThumb) {
        this.categoryThumb = categoryThumb;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryThumb() {
        return categoryThumb;
    }

    public String getDescription() {
        return description;
    }
}
