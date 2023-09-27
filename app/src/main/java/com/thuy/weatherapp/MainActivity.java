package com.thuy.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private EditText mEditText;
    private Button mButton;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "MainActivity: onCreate()");
        setContentView(R.layout.activity_main);

        mContext = this;

        mTextViewCityName = findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        mEditText = findViewById(R.id.edit_text);
        mButton = findViewById(R.id.button_1);
        mButton.setOnClickListener(onClickListener);
        //mButton.setOnClickListener(v -> Log.d("Thuy", "Bouton cliqué"));
        /**Toast.makeText(this, mTextViewCityName.getText().toString(), Toast.LENGTH_SHORT).show();
         ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
         if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
         Log.d("Thuy", "Oui je suis connecté");
         } else {
         Log.d("Thuy", "Non j'ai rien");
         findViewById(R.id.linear_layout_main).setVisibility(View.INVISIBLE);
         findViewById(R.id.text_view_no_connection).setVisibility(View.VISIBLE);
         }*/
    }

    //public void onClickButton(View view){
    //    Boolean isButton1 = view==findViewById(R.id.button_1);
    //    String msg = isButton1 ? "Clic sur bouton 1" : "Clic sur bouton 3";
    //    Log.d("Thuy", "Bouton 1 cliqué ? " + isButton1);
    //    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    //}

    //public void OnClickListener(View view){
    //    Log.d("Thuy", "Bouton cliqué");
    //    Toast.makeText(this, "Clic sur bouton 2", Toast.LENGTH_SHORT).show();
    //}
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
// actions quand notre bouton est cliqué

            Intent intent = new Intent(mContext, FavoriteActivity.class);
            intent.putExtra("message", mEditText.getText().toString());
            startActivity(intent);
            //Log.d("Thuy", "Clic sur bouton 2");
        }
    };

    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MainActivity: onDestroy()");
    }
}