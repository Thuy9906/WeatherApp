package com.thuy.weatherapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import com.thuy.weatherapp.R;
import com.thuy.weatherapp.models.City;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private ImageView mImageViewCityDescription;
    private EditText mEditText;
    private Button mButton;

    private Context mContext;
    private OkHttpClient mOkHttpClient;
    public City mCurrentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "MainActivity: onCreate()");
        setContentView(R.layout.activity_main);

        mContext = this;

        mTextViewCityName = findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        mImageViewCityDescription = findViewById(R.id.image_view_city_desc);
        mEditText = findViewById(R.id.edit_text);
        mButton = findViewById(R.id.button_1);
        mButton.setOnClickListener(onClickListener);
        Toast.makeText(this, mTextViewCityName.getText().toString(), Toast.LENGTH_SHORT).show();

        mOkHttpClient = new OkHttpClient();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Log.d("Thuy", String.valueOf(networkInfo));
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            Log.d("Thuy", "Oui je suis connecté");
            Request request = new Request.Builder()
                    .url("").build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                                                       @Override
                                                       public void onFailure(Call call, IOException e) {
                                                           Log.d("TAG", "request failed");
                                                       }

                                                       @Override
                                                       public void onResponse(Call call, Response response) throws IOException {
                                                           if (response.isSuccessful()) {
                                                               final String stringJson = response.body().string();
                                                               Log.d("TAG", stringJson);
                                                               runOnUiThread(new Runnable() {
                                                                   public void run() {
                                                                       // Code exécuté dans le Thread principale
                                                                       try {
                                                                           updateUi(stringJson);
                                                                       } catch (JSONException e) {
                                                                           //throw new RuntimeException(e);
                                                                       }
                                                                   }
                                                               });
                                                           }

                                                       }
                                                   }
            );
        } else {
            Log.d("Thuy", "Non j'ai rien");
            findViewById(R.id.linear_layout_main).setVisibility(View.INVISIBLE);
            findViewById(R.id.text_view_no_connection).setVisibility(View.VISIBLE);
        }

    }

    public void updateUi(String stringJson) throws JSONException {
        this.mCurrentCity = new City(stringJson);
        mTextViewCityName.setText(mCurrentCity.mName);
        mImageViewCityDescription.setImageResource(mCurrentCity.mWeatherResIconWhite);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
// actions quand notre bouton est cliqué
            Intent intent = new Intent(mContext, FavoriteActivity.class);
            intent.putExtra("message", mEditText.getText().toString());
            startActivity(intent);
        }
    };

//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("TAG", "MainActivity: onDestroy()");
//    }
}