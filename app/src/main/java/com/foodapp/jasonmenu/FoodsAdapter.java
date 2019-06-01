package com.foodapp.jasonmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder> {

    private ButtonClickListener listener;
    private ArrayList<OrderModel> orderList;


    public FoodsAdapter(ButtonClickListener listener, ArrayList<OrderModel> orderList) {
        this.listener = listener;
        this.orderList = orderList;
    }


    @Override
    public FoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(MainActivity.TAG, "onCreateViewHolder post size = " + getItemCount());


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods,
                parent, false);

        FoodsAdapter.ViewHolder holder = new FoodsAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(FoodsAdapter.ViewHolder holder, final int position) {
        Log.d(MainActivity.TAG, "FoodsAdapter onBindViewHolder = " + getItemCount());

        holder.nameFoodsTextView.setText(orderList.get(position).getName());
        holder.descriptionTextView.setText(orderList.get(position).getDescription());
        holder.weightTextView.setText(orderList.get(position).getWeight());
        holder.timeTextView.setText(orderList.get(position).getTime());
        holder.priceTextView.setText(orderList.get(position).getPrice());
        holder.amountTextView.setText(String.valueOf(orderList.get(position).getAmount()));

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(v, position);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderList == null)
            return 0;
        return orderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameFoodsTextView;
        TextView descriptionTextView;
        TextView weightTextView;
        TextView timeTextView;
        TextView priceTextView;
        TextView amountTextView;
        Button plusButton;
        Button minusButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameFoodsTextView = itemView.findViewById(R.id.nameFoodsTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            plusButton = itemView.findViewById(R.id.plusButton);
            minusButton = itemView.findViewById(R.id.minusButton);
        }
    }
}
