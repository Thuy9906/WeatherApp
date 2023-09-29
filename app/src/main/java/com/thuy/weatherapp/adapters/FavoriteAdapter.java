package com.thuy.weatherapp.adapters;

import static com.thuy.weatherapp.utils.Util.saveFavouriteCities;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.thuy.weatherapp.R;
import com.thuy.weatherapp.models.City;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<City> mCities;

    // Constructor
    public FavoriteAdapter(Context mContext, ArrayList<City> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = mCities.get(position);
        holder.mCity = city;
        holder.mTextViewCityName.setText(city.mName);
        holder.mImageViewCityDescription.setImageResource(city.mWeatherIcon);
        holder.mTextViewCityDescription.setText(city.mDescription);
        holder.mTextViewCityTemperature.setText(city.mTemperature);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    // Classe holder qui contient la vue d’un item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewCityName;
        public TextView mTextViewCityDescription;
        public TextView mTextViewCityTemperature;
        public ImageView mImageViewCityDescription;
        public City mCity;

        public ViewHolder(View view) {
            super(view);
            mTextViewCityName = view.findViewById(R.id.text_view_item_city_name);
            mTextViewCityDescription = view.findViewById(R.id.text_view_item_city_desc);
            mTextViewCityTemperature = view.findViewById(R.id.text_view_item_city_temp);
            mImageViewCityDescription = view.findViewById(R.id.image_view_item_city_desc);
            view.setOnLongClickListener(v -> removeCity());
        }

        private boolean removeCity() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Voulez vous supprimer " + mCity.mName + " ?");

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("TAG", "confirmer");
                    mCities.remove(mCity);
                    //mCities.add(city);
                    notifyDataSetChanged();
                    saveFavouriteCities(mContext, mCities);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.create().show();
            return false;
        }
    }
}
