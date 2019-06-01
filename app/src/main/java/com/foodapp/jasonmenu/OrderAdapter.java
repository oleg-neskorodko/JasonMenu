package com.foodapp.jasonmenu;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Bundle bundle1;
    private ButtonClickListener listener;
    private ArrayList<Integer> amountOrdered;


    public OrderAdapter(ButtonClickListener listener, Bundle bundle1, ArrayList<Integer> amountOrdered) {
        this.listener = listener;
        this.bundle1 = bundle1;
        this.amountOrdered = amountOrdered;
    }


    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(MainActivity.TAG, "onCreateViewHolder post size = " + getItemCount());


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods,
                parent, false);

        OrderAdapter.ViewHolder holder = new OrderAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, final int position) {
        Log.d(MainActivity.TAG, "FoodsAdapter onBindViewHolder = " + getItemCount());

        holder.nameFoodsTextView.setText(bundle1.getString("name" + position));
        holder.descriptionTextView.setText(bundle1.getString("description" + position));
        holder.weightTextView.setText(bundle1.getString("weight" + position));
        holder.timeTextView.setText(bundle1.getString("time" + position));
        holder.priceTextView.setText(bundle1.getString("price" + position));
        holder.amountTextView.setText(String.valueOf(amountOrdered.get(position)));

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
        if (bundle1 == null)
            return 0;
        return bundle1.getInt("bundle_size");
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
