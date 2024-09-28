package com.example.tastytactics.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @PrimaryKey
    @NonNull
    @SerializedName("idIngredient")
    private String id;
    @SerializedName("strIngredient")
    private String ingredient;
    @SerializedName("strDescription")
    private String description;
    @SerializedName("strType")
    private String type;

    @NonNull
    public String getId() {
        return id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
