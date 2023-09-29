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

import java.io.IOException;
import java.util.ArrayList;

import com.thuy.weatherapp.databinding.ActivityFavoriteBinding;

import org.json.JSONException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
//        City city1 = new City("Montréal", "Légères pluies", "22°C", R.drawable.weather_rainy_grey);
//        City city2 = new City("New York", "Ensoleillé", "22°C", R.drawable.weather_sunny_grey);
//        City city3 = new City("Paris", "Nuageux", "24°C", R.drawable.weather_foggy_grey);
//        City city4 = new City("Toulouse", "Pluies modérées", "20°C", R.drawable.weather_rainy_grey);
//        mCities.add(city1);
//        mCities.add(city2);
//        mCities.add(city3);
//        mCities.add(city4);

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

                retrieveCityJsonFromApiRequest(editTextCity.getText().toString());

                Log.d("TAG", "confirmer");

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void retrieveCityJsonFromApiRequest(String cityName) {

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=01897e497239c8aff78d9b8538fb24ea&units=metric&lang=fr").build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
                                                        @Override
                                                        public void onFailure(Call call, IOException e) {
                                                        }

                                                        @Override
                                                        public void onResponse(Call call, Response response) throws IOException {
                                                            if (response.isSuccessful()) {

//                                                           String strJson = response.body().string();
//                                                           Log.d("TAG", "retrieveCityJsonFromApiRequest" + strJson);
                                                                final String strJson = response.body().string();
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        updateUi(strJson);
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
        );

    }

    private void updateUi(String strJson) {
        City city = null;
        try {
            city = new City(strJson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        mCities.add(city);
        mAdapter.notifyDataSetChanged();
    }

//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("TAG", "FavoriteActivity: onDestroy()");
//    }
}