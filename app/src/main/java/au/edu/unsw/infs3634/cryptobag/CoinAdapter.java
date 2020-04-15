package au.edu.unsw.infs3634.cryptobag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private MainActivity mParentActivity;
    public static final String ARG_ITEM_ID = "item_id";
    private List<Coin> mCoins;
    private boolean mTwoPane;


        public CoinAdapter(MainActivity parent, List<Coin> coins, boolean twoPane) {
                mParentActivity = parent;
                mCoins = coins;
                mTwoPane = twoPane;
        }

    public void setCoins(List<Coin> coins) { mCoins.clear();
        mCoins.addAll(coins);
        notifyDataSetChanged(); }

            @Override
            public CoinAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_row, parent, false);
                return new CoinViewHolder(v);
            }


            @Override
            public void onBindViewHolder(CoinViewHolder holder, int position) {
                Coin coin = mCoins.get(position);
                holder.name.setText(coin.getName());
                holder.value.setText(NumberFormat.getCurrencyInstance().format(Double.valueOf(coin.getPriceUsd())));
                holder.change.setText((coin.getPercentChange24h()) + " %");
            }

            @Override
            public int getItemCount() {
                return mCoins.size();
            }



    public class CoinViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value, change;

        public CoinViewHolder(View v) {

            super(v);
            name = v.findViewById(R.id.tvName);
            value = v.findViewById(R.id.tvValue);
            change = v.findViewById(R.id.tvChange);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Coin coin = mCoins.get(getAdapterPosition());

                    if(mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ARG_ITEM_ID,coin.getId());
                        DetailFragment fragment = new DetailFragment();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(ARG_ITEM_ID, coin.getId());
                        context.startActivity(intent);
                    }

                }
            });

        }

        public void setCoins(List<Coin> coins) {
            mCoins.clear();
            mCoins.addAll(coins);
            notifyDataSetChanged();




        }


    }
}

