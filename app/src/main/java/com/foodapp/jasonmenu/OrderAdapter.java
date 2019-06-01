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

    private ButtonClickListener listener;
    private ArrayList<OrderModel> totalList;


    public OrderAdapter(ButtonClickListener listener, ArrayList<OrderModel> totalList) {
        this.listener = listener;
        this.totalList = totalList;
    }


    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Log.d(MainActivity.TAG, "onCreateViewHolder post size = " + getItemCount());


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,
                parent, false);

        OrderAdapter.ViewHolder holder = new OrderAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, final int position) {
        //Log.d(MainActivity.TAG, "FoodsAdapter onBindViewHolder = " + getItemCount());

        holder.nameOrderItemTextView.setText(totalList.get(position).getName());
        holder.descriptionOrderItemTextView.setText(totalList.get(position).getDescription());
        holder.weightOrderItemTextView.setText(totalList.get(position).getWeight());
        holder.timeOrderItemTextView.setText(totalList.get(position).getTime());
        holder.priceOrderItemTextView.setText(totalList.get(position).getPrice());
        holder.amountOrderItemTextView.setText(String.valueOf(totalList.get(position).getAmount()));

        holder.plusOrderItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(v, position);
            }
        });

        holder.minusOrderItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (totalList == null)
            return 0;
        return totalList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameOrderItemTextView;
        TextView descriptionOrderItemTextView;
        TextView weightOrderItemTextView;
        TextView timeOrderItemTextView;
        TextView priceOrderItemTextView;
        TextView amountOrderItemTextView;
        Button plusOrderItemButton;
        Button minusOrderItemButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameOrderItemTextView = itemView.findViewById(R.id.nameOrderItemTextView);
            descriptionOrderItemTextView = itemView.findViewById(R.id.descriptionOrderItemTextView);
            weightOrderItemTextView = itemView.findViewById(R.id.weightOrderItemTextView);
            timeOrderItemTextView = itemView.findViewById(R.id.timeOrderItemTextView);
            priceOrderItemTextView = itemView.findViewById(R.id.priceOrderItemTextView);
            amountOrderItemTextView = itemView.findViewById(R.id.amountOrderItemTextView);
            plusOrderItemButton = itemView.findViewById(R.id.plusOrderItemButton);
            minusOrderItemButton = itemView.findViewById(R.id.minusOrderItemButton);
        }
    }
}
