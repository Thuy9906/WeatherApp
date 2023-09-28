package com.thuy.weatherapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.thuy.weatherapp.R;
import com.thuy.weatherapp.adapters.FavoriteAdapter;
import com.thuy.weatherapp.models.City;

import java.util.ArrayList;

import com.thuy.weatherapp.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private ArrayList<City> mCities;
    private RecyclerView mRecyclerViewCities;
    private RecyclerView.Adapter mAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "FavoriteActivity: onCreate()");
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

//        FloatingActionButton fab = binding.fab;
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Bundle extras = getIntent().getExtras();
        String strMessage = extras.getString("message");
        binding.textViewFromExtra.setText(strMessage);

        mCities = new ArrayList<>();
        City city1 = new City("Montréal", "Légères pluies", "22°C", R.drawable.weather_rainy_grey);
        City city2 = new City("New York", "Ensoleillé", "22°C", R.drawable.weather_sunny_grey);
        City city3 = new City("Paris", "Nuageux", "24°C", R.drawable.weather_foggy_grey);
        City city4 = new City("Toulouse", "Pluies modérées", "20°C", R.drawable.weather_rainy_grey);
        mCities.add(city1);
        mCities.add(city2);
        mCities.add(city3);
        mCities.add(city4);

        mContext = this;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewCities = binding.included.recyclerViewCities;
        mRecyclerViewCities.setLayoutManager(layoutManager);
        mAdapter = new FavoriteAdapter(mContext, mCities);
        mRecyclerViewCities.setAdapter(mAdapter);
    }

    public void onClickSearch(View view) {
        Log.d("TAG", "search");
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.search_city);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
        builder.setView(v);
        final EditText editTextCity = (EditText) v.findViewById(R.id.edit_text_dialog_city);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("TAG", "confirmer");
                City city = new City(editTextCity.getText().toString(), "Ensoleillé", "28°C", R.drawable.weather_sunny_grey);
                mCities.add(city);
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("TAG", "FavoriteActivity: onDestroy()");
//    }
}