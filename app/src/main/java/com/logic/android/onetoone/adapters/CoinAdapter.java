package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Coins;
import com.logic.android.onetoone.models.CountryCoin;

import java.util.ArrayList;
import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {

   public interface  OnCoinClickListener{
        public void onClick(int coin);
    }

    private static final String TAG = "CoinAdapter";
    RecyclerView recyclerView;
    List<CountryCoin> countryCoinList=new ArrayList<>();
    List<Coins> coinsList =new ArrayList<>();
    Context context;
    OnCoinClickListener listener;


    public CoinAdapter(RecyclerView recyclerView, List<CountryCoin> countryCoinList,
                       List<Coins> coinsList, Context context, OnCoinClickListener listener) {
        this.recyclerView = recyclerView;
        this.countryCoinList = countryCoinList;
        this.coinsList = coinsList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater
                .from(context)
                .inflate(R.layout.custom_coin_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

      //  List<Coins> coinsListList= countryCoinList.get(i).getCoins();
        Coins coins= coinsList.get(i);
        viewHolder.button.setText(""+coins.getCoin());
        Log.e(TAG, "onBindViewHolder: "+coinsList.size());
        Log.e(TAG, "onBindViewHolder: "+ coins.getCoin());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(Integer.parseInt(viewHolder.button.getText().toString()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            button=itemView.findViewById(R.id.coinsAddDemo);
        }
    }
}
