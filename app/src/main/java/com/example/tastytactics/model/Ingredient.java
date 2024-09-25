package com.example.tastytactics.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Ingredient {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idIngredient")
    public String id;
    @ColumnInfo(name = "strIngredient")
    public String ingredient;
    @ColumnInfo(name = "strDescription")
    public String description;
    @ColumnInfo(name = "strType")
    public String type;
}
