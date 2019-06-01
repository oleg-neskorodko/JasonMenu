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

public class FoodsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FoodsAdapter adapter;
    private TextView headerFoodsTextView;
    private Bundle bundle;
    private ArrayList<OrderModel> orderList;
    private ArrayList<OrderModel> totalList;
    private Map<Integer, OrderModel> orderedMap;
    private SharedPreferences sPref;
    //private SharedPreferences sPref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.foods_layout, null);
        Log.d(MainActivity.TAG, "FoodsFragment onCreateView");

        recyclerView = v.findViewById(R.id.recycler_view);
        headerFoodsTextView = v.findViewById(R.id.headerFoodsTextView);
        orderedMap = new HashMap<>();



        sPref = getActivity().getSharedPreferences("Order", getActivity().MODE_PRIVATE);
        totalList = new ArrayList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<OrderModel>>(){}.getType();
        Log.d(MainActivity.TAG, "input = " + sPref.getString("order", "[]"));
        totalList = gson.fromJson(sPref.getString("order", "[]"), listType);

        //Log.d(MainActivity.TAG, "total = " + totalList.get(2).getAmount() + totalList.get(0).getName());




        bundle = this.getArguments();
        orderList = new ArrayList<>();
        if (bundle != null) {
            Log.d(MainActivity.TAG, "FoodsFragment bundle NOT NULL");

            headerFoodsTextView.setText(bundle.getString("category"));

            for (int i = 0; i < bundle.getInt("bundle_size"); i++) {
                orderList.add(i, new OrderModel());
                orderList.get(i).setParams(bundle.getInt("id" + i),
                        bundle.getString("name" + i),
                        bundle.getString("weight" + i),
                        bundle.getString("time" + i),
                        bundle.getString("price" + i),
                        bundle.getString("description" + i),
                        0);
            }

            Log.d(MainActivity.TAG, "order = " + orderList.get(0).getAmount());



            int replaced = 0;
            for (int i = 0; i < totalList.size(); i++) {
                for (int j = 0; j < orderList.size(); j++) {
                    Log.d(MainActivity.TAG, "i = " + i + "j = " + j + "ids = " + totalList.get(i).getId() + " " + orderList.get(j).getId());
                    if (totalList.get(i).getId().equals(orderList.get(j).getId())) {
                        Log.d(MainActivity.TAG, "here");
                        orderList.get(j).setAmount(totalList.get(i).getAmount());
                        replaced++;
                    }
                }
            }

            Log.d(MainActivity.TAG, "replaced = " + replaced);


            for (int i = 0; i < orderList.size(); i++) {
                orderedMap.put(orderList.get(i).getId(), orderList.get(i));
            }

            //Log.d(MainActivity.TAG, "total = " + totalList.get(2).getAmount() + totalList.get(2).getName());
            //Log.d(MainActivity.TAG, "order = " + orderList.get(0).getAmount());

            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new FoodsAdapter(new ButtonClickListener() {
                @Override
                public void onButtonClick(View v, int position) {
                    OrderModel orderModel = orderList.get(position);
                    switch (v.getId()) {
                        case R.id.plusButton:
                            if (orderedMap.containsKey(orderModel.getId())) {
                                orderModel.setAmount(orderModel.getAmount() + 1);
                                //orderedMap.get(orderModel.getId()).setAmount(orderModel.getAmount() + 1);
                            } else {
                                orderModel.setAmount(1);
                                orderedMap.put(orderModel.getId(), orderModel);
                            }

                            recyclerView.getAdapter().notifyDataSetChanged();
                            break;
                        case R.id.minusButton:
                            if (orderedMap.containsKey(orderModel.getId())) {
                                orderModel.setAmount(orderModel.getAmount() - 1);
                                //orderedMap.get(orderModel.getId()).setAmount(orderModel.getAmount() - 1);
                                if (orderedMap.get(orderModel.getId()).getAmount() == 0)
                                    orderedMap.remove(orderModel.getId());
                            }

                            recyclerView.getAdapter().notifyDataSetChanged();
                            break;

                    }
                }

            }, orderList);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new SpaceItemDecoration(Constants.RECYCLER_SPACE));


        } else Log.d(MainActivity.TAG, "FoodsFragment bundle NULL");

        return v;
    }

    @Nullable
    private OrderModel getById(int id) {
        for (OrderModel orderModel : orderList) {
            if (orderModel.getId() == id)
                return orderModel;
        }
        return null;
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(MainActivity.TAG, "ordered amount = " + orderList.get(0).getAmount());
        int replaced = 0;
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getAmount() > 0) {
                if (totalList.size() > 0) {
                    int match = 0;
                    for (int i = 0; i < totalList.size(); i++) {
                        if (totalList.get(i).getId().equals(orderList.get(j).getId())) {
                            totalList.get(i).setAmount(orderList.get(j).getAmount());
                            match++;
                        }
                    }
                    if (match == 0) {
                        totalList.add(orderList.get(j));
                    }
                } else totalList.add(orderList.get(j));

            } else {
                int pos = -1;
                for (int i = 0; i < totalList.size(); i++){
                    if (totalList.get(i).getId().equals(orderList.get(j).getId())) {
                        pos = i;
                    }
                }
                if (pos >= 0) {
                    totalList.remove(pos);
                }
            }
        }
        Log.d(MainActivity.TAG, "replaced = " + replaced);
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


