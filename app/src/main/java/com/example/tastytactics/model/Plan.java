package com.example.tastytactics.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "plan_table")
public class Plan {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Embedded
    Meal meal;
    private Date date;

    public Plan(Meal _meal, Date _date) {
        meal = _meal;
    }

    public Plan() {}

    public void setId(int id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
