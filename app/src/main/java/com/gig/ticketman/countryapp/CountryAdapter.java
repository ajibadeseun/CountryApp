package com.gig.ticketman.countryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAMILOLA on 8/29/2017.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {
    private Context mContext;
    private List<Country> countryList;

    public CountryAdapter(Context context, List<Country> countryList) {

        this.mContext = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Country country = countryList.get(position);
        holder.countryName.setText(country.getCountryName());
        holder.countryCurrency.setText(country.getCountryCurrency());
        holder.countryLanguage.setText(country.getCountryLanguage());
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }



    // clear the data on the recylerview
    public void clear(){
        countryList.clear();
    }

    // remove swiped item from the country list
    public void removeItem(int position) {
        countryList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    // restore the deleted item from the country list
    public void restoreItem(Country country, int position) {
        countryList.add(position, country);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView countryName;
        public TextView countryCurrency;
        public TextView countryLanguage;
        public RelativeLayout viewForeground;
        public RelativeLayout viewBackground;

        public MyViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryCurrency = (TextView) itemView.findViewById(R.id.country_currency_name);
            countryLanguage = (TextView) itemView.findViewById(R.id.country_language);
            viewForeground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
        }
    }
}
