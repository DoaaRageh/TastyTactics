package com.example.tastytactics.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class Category {
    @PrimaryKey
    @NonNull
    @SerializedName("idCategory")
    public String id;
    @SerializedName("strCategory")
    public String category;
    @SerializedName("strCategoryThumb")
    public String categoryThumb;
    @SerializedName("strCategoryDescription")
    public String description;
}
