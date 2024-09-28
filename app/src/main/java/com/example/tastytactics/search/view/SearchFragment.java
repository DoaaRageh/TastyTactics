package com.example.tastytactics.search.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tastytactics.R;
import com.example.tastytactics.db.MealsLocalDataSourceImpl;
import com.example.tastytactics.home.presenter.CategoryPresenter;
import com.example.tastytactics.home.presenter.HomePresenter;
import com.example.tastytactics.home.view.HomeAdapter;
import com.example.tastytactics.home.view.OnMealClickListener;
import com.example.tastytactics.model.Meal;
import com.example.tastytactics.model.MealsRepositoryImpl;
import com.example.tastytactics.network.MealsRemoteDataSourceImpl;
import com.example.tastytactics.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchView, OnMealClickListener {
    private SearchAdapter searchAdapter;
    private SearchPresenter searchPresenter;
    private RecyclerView searchRecyclerView;
    LinearLayoutManager layoutManager;

    private EditText editSearch;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        editSearch = v.findViewById(R.id.editSearch);
        searchRecyclerView = v.findViewById(R.id.searchRecyclerView);

        searchRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());

        searchAdapter = new SearchAdapter(getContext(), new ArrayList<>(), this);
        searchPresenter = new SearchPresenter(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance().getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())));
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(searchAdapter);

        searchPresenter.serchByCategory(editSearch.getText().toString());

        return v;
    }

    @Override
    public void showData(List<Meal> meals) {

    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void onMealClick(String title, String instructions, String thumb) {

    }

    @Override
    public void onFavClick(Meal meal) {

    }
}