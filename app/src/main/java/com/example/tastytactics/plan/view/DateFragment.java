package com.example.tastytactics.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.model.Plan;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.plan.presenter.PlanPresenter;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends DialogFragment implements PlanView{
    CalendarView calendar;
    private Meal meal;
    private PlanPresenter planPresenter;
    public DateFragment(Meal _meal) {
        meal = _meal;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_date, container, false);
        calendar = v.findViewById(R.id.calendarView);
        Calendar selectedDateCalendar = Calendar.getInstance();
        calendar.setMinDate(System.currentTimeMillis());
        selectedDateCalendar.add(Calendar.DAY_OF_YEAR, 6);
        long maxDate = selectedDateCalendar.getTimeInMillis();

        calendar.setMaxDate(maxDate);

        planPresenter = new PlanPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int selectedYear, int selectedMonth, int selectedDay) {
                selectedDateCalendar.set(selectedYear, selectedMonth, selectedDay, 0, 0, 0);
                selectedDateCalendar.set(Calendar.MILLISECOND, 0);
                Plan plan = new Plan(meal, selectedDateCalendar.getTime());
                planPresenter.addToPlan(plan, selectedDateCalendar.getTime());
                dismiss();
            }
        });

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Make the dialog background transparent
        }

        return v;
    }

    @Override
    public void showData(List<Plan> meals) {

    }

    @Override
    public void showErrMsg(String error) {

    }
}