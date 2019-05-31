package com.foodapp.jasonmenu;

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
import android.widget.TextView;

public class FoodsFragment extends Fragment {

private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FoodsAdapter adapter;
    private Bundle bundle;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_layout, null);
        Log.d(MainActivity.TAG, "FoodsFragment onCreateView");

        recyclerView = v.findViewById(R.id.recycler_view);

        bundle = this.getArguments();
        if (bundle != null) {
            Log.d(MainActivity.TAG, "FoodsFragment bundle NOT NULL");

            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new FoodsAdapter(getActivity(), bundle);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);


        } else Log.d(MainActivity.TAG, "FoodsFragment bundle NULL");





        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
