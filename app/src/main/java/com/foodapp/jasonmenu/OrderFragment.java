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

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private OrderAdapter adapter;
    private ArrayList<Integer> amountOrdered;
    //private SharedPreferences sPref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.order_layout, null);
        Log.d(MainActivity.TAG, "OrderFragment onCreateView");

        recyclerView = v.findViewById(R.id.recycler_view);


        amountOrdered = new ArrayList<>();


        layoutManager = new LinearLayoutManager(getActivity());


        SharedPreferences sPref = getActivity().getSharedPreferences("Order", getActivity().MODE_PRIVATE);
        Log.d(MainActivity.TAG, "mark1");
        int orderSize = sPref.getInt("order_size", 0);
        Log.d(MainActivity.TAG, "mark2");
        ArrayList<Integer> orderIds = new ArrayList<>();
        ArrayList<Integer> orderAmount = new ArrayList<>();
        Log.d(MainActivity.TAG, "mark3 = " + orderSize);
        for (int i = 0; i < orderSize; i++) {
            orderIds.set(i, sPref.getInt(("food_id" + i), 0));
            orderAmount.set(i, sPref.getInt(("food_id" + i), 0));
            Log.d(MainActivity.TAG, "order" + orderIds.get(i) + orderAmount.get(i));
        }



/*            adapter = new FoodsAdapter(new ButtonClickListener() {

                @Override
                public void onButtonClick(View v, int position) {
                    switch (v.getId()) {
                        case R.id.plusButton:
                            Log.d(MainActivity.TAG, "PLUS" + position + " " + amountOrdered.size());
                            amountOrdered.set(position, (amountOrdered.get(position) + 1));
                            recyclerView.getAdapter().notifyDataSetChanged();
                            break;
                        case R.id.minusButton:
                            Log.d(MainActivity.TAG, "MINUS" + position);
                            if (amountOrdered.get(position) != 0) {
                                amountOrdered.set(position, (amountOrdered.get(position) - 1));
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }

                            break;

                    }
                }

            }, bundle, amountOrdered);*/

        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new SpaceItemDecoration(Constants.RECYCLER_SPACE));


        return v;
    }

    @Override
    public void onStop() {
        super.onStop();


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
