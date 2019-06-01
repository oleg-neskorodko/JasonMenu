package com.foodapp.jasonmenu;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ItemClickListener listener;
    private OrderAdapter adapter;
    private SharedPreferences sPref;
    private ArrayList<OrderModel> totalList;
    private TextView numberOrderTextView;
    private TextView timeOrderTextView;
    private TextView priceOrderTextView;
    private Button confirmOrderButton;
    private Button clearOrderButton;
    private int amount;
    private int time;
    private int price;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.order_layout, null);
        Log.d(MainActivity.TAG, "OrderFragment onCreateView");

        recyclerView = v.findViewById(R.id.recycler_view);
        numberOrderTextView = v.findViewById(R.id.numberOrderTextView);
        timeOrderTextView = v.findViewById(R.id.timeOrderTextView);
        priceOrderTextView = v.findViewById(R.id.priceOrderTextView);
        confirmOrderButton = v.findViewById(R.id.confirmOrderButton);
        clearOrderButton = v.findViewById(R.id.clearOrderButton);

        layoutManager = new LinearLayoutManager(getActivity());

        sPref = getActivity().getSharedPreferences("Order", getActivity().MODE_PRIVATE);
        totalList = new ArrayList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<OrderModel>>(){}.getType();
        Log.d(MainActivity.TAG, "input = " + sPref.getString("order", "[]"));
        totalList = gson.fromJson(sPref.getString("order", "[]"), listType);

        countVariables();

            adapter = new OrderAdapter(new ButtonClickListener() {

                @Override
                public void onButtonClick(View v, int position) {
                    switch (v.getId()) {
                        case R.id.plusOrderItemButton:
                            Log.d(MainActivity.TAG, "PLUS" + position);
                            totalList.get(position).setAmount(totalList.get(position).getAmount() + 1);
                            recyclerView.getAdapter().notifyDataSetChanged();
                            countVariables();
                            break;
                        case R.id.minusOrderItemButton:
                            Log.d(MainActivity.TAG, "MINUS" + position);
                            if (totalList.get(position).getAmount() != 0) {
                                totalList.get(position).setAmount(totalList.get(position).getAmount() - 1);
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                            break;

                    }
                }

            }, totalList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(Constants.RECYCLER_SPACE));

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.TAG, "confirm button" + time);
                if (time !=0) {
                    listener.onConfirmButtonClick(time);
                }
            }
        });

        clearOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.TAG, "clear button");
                totalList.clear();
                sPref = getActivity().getSharedPreferences("Order", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("order", "[]");
                ed.commit();
                recyclerView.getAdapter().notifyDataSetChanged();
                numberOrderTextView.setText(String.valueOf(amount = 0));
                timeOrderTextView.setText(String.valueOf(time = 0));
                priceOrderTextView.setText(String.valueOf(price = 0));
            }
        });
        return v;
    }

    public void countVariables() {
        amount = 0;
        time = 0;
        price = 0;

        amount = totalList.size();
        for (int i = 0; i < totalList.size(); i++) {
            if (time < Integer.parseInt(totalList.get(i).getTime())) {
                time = Integer.parseInt(totalList.get(i).getTime());
            }
            price += (Integer.parseInt(totalList.get(i).getPrice()) * totalList.get(i).getAmount());
        }

        numberOrderTextView.setText(String.valueOf(amount));
        timeOrderTextView.setText(String.valueOf(time));
        priceOrderTextView.setText(String.valueOf(price));
    }

    @Override
    public void onStop() {
        super.onStop();

        for (int i = 0; i < totalList.size(); i++) {
            if (totalList.get(i).getAmount() == 0) {
                totalList.remove(i);
            }
        }


        Map<Integer, OrderModel> totalMap = new HashMap<>();
        for (int i = 0; i < totalList.size(); i++) {
            totalMap.put(totalList.get(i).getId(), totalList.get(i));
        }
        Log.d(MainActivity.TAG, "map = " + totalMap.values());

        sPref = getActivity().getSharedPreferences("Order", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(totalMap.values());
        ed.putString("order",json);
        ed.commit();
        Log.d(MainActivity.TAG, "order:\n" + json);
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int spaceWidth;

        public SpaceItemDecoration(int spaceWidth) {
            this.spaceWidth = spaceWidth;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = spaceWidth;
            outRect.left = spaceWidth;
            outRect.right = spaceWidth;
        }
    }
}
