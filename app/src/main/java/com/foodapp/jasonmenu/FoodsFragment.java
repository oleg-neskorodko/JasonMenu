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

public class FoodsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FoodsAdapter adapter;
    private Bundle bundle;
    private ArrayList<Integer> amountOrdered;
    //private SharedPreferences sPref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_layout, null);
        Log.d(MainActivity.TAG, "FoodsFragment onCreateView");

        recyclerView = v.findViewById(R.id.recycler_view);

        bundle = this.getArguments();
        amountOrdered = new ArrayList<>();
        for (int i = 0; i < bundle.getInt("bundle_size"); i++) {
            amountOrdered.add(i, 0);
        }

        if (bundle != null) {
            Log.d(MainActivity.TAG, "FoodsFragment bundle NOT NULL");

            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new FoodsAdapter(new ButtonClickListener() {

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

            }, bundle, amountOrdered);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new SpaceItemDecoration(Constants.RECYCLER_SPACE));


        } else Log.d(MainActivity.TAG, "FoodsFragment bundle NULL");


        return v;
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sPref = getActivity().getSharedPreferences("Order", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        int orderSize = 0;

        for (int i = 0; i < amountOrdered.size(); i++) {
            if (amountOrdered.get(i) != 0) {
                ed.putInt("meal" + bundle.getInt("id" + i), amountOrdered.get(i));
                orderSize++;
            }
        }
        ed.putInt("order_size", sPref.getInt("order_size", 0) + orderSize);
        ed.commit();
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


