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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MenuAdapter adapter;
    private ItemClickListener listener;
    private List<MenuModel> posts;
    private LinearLayout orderLayout;
    private SharedPreferences sPref;
    private ArrayList<OrderModel> totalList;
    private TextView itemsMenuTextView;
    private TextView timeMenuTextView;
    private TextView priceMenuTextView;



    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.menu_layout, null);
        Log.d(MainActivity.TAG, "fragment1 onCreateView");


        orderLayout = v.findViewById(R.id.orderLayout);
        itemsMenuTextView = v.findViewById(R.id.itemsMenuTextView);
        timeMenuTextView = v.findViewById(R.id.timeMenuTextView);
        priceMenuTextView = v.findViewById(R.id.priceMenuTextView);
        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderClick();
            }
        });


        sPref = getActivity().getSharedPreferences("Order", getActivity().MODE_PRIVATE);
        totalList = new ArrayList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<OrderModel>>(){}.getType();
        Log.d(MainActivity.TAG, "input = " + sPref.getString("order", "[]"));
        totalList = gson.fromJson(sPref.getString("order", "[]"), listType);

        int amount = 0;
        int time = 0;
        int price = 0;

        amount = totalList.size();
        for (int i = 0; i < totalList.size(); i++) {
            if (time < Integer.parseInt(totalList.get(i).getTime())) {
                time = Integer.parseInt(totalList.get(i).getTime());
            }
            price += (Integer.parseInt(totalList.get(i).getPrice()) * totalList.get(i).getAmount());
        }
        itemsMenuTextView.setText(String.valueOf(amount));
        timeMenuTextView.setText(String.valueOf(time));
        priceMenuTextView.setText(String.valueOf(price));


        String text = "menu09.json";
        byte[] buffer = null;
        InputStream is;
        try {
            is = getActivity().getAssets().open(text);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str_data = new String(buffer);

        posts = new ArrayList<>();

        Gson gson2 = new Gson();
        Type listType2 = new TypeToken<List<MenuModel>>(){}.getType();
        posts = gson2.fromJson(str_data, listType2);





        Log.d(MainActivity.TAG, "posts.size() = " + posts.size());
        ArrayList<Food> menu = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            for (int j = 0; j < posts.get(i).getFoods().size(); j++) {
                Log.d(MainActivity.TAG, "adding = " + posts.get(i).getFoods().size());
                menu.add(i, posts.get(i).getFoods().get(j));
            }
            Log.d(MainActivity.TAG, "added = " + posts.get(i).getFoods().size());
        }
        Log.d(MainActivity.TAG, "total added = " + menu.size());

        menu.get(2).getId();

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MenuAdapter(getActivity(), posts);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new SpaceItemDecoration(Constants.RECYCLER_SPACE));

        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                Log.d(MainActivity.TAG, "onClick position = " + position);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("bundle_size", posts.get(position).getFoods().size());
                bundle1.putString("category", posts.get(position).getName());
                for (int i = 0; i < posts.get(position).getFoods().size(); i++) {
                    bundle1.putString("name" + i, posts.get(position).getFoods().get(i).getName());
                    bundle1.putString("description" + i, posts.get(position).getFoods().get(i).getDescription());
                    bundle1.putInt("id" + i, posts.get(position).getFoods().get(i).getId());
                    bundle1.putString("weight" + i, posts.get(position).getFoods().get(i).getWeight());
                    bundle1.putString("time" + i, posts.get(position).getFoods().get(i).getTime());
                    bundle1.putString("price" + i, posts.get(position).getFoods().get(i).getPrice());
                }

                listener.onItemClick(bundle1);
            }
        });
        Log.d(MainActivity.TAG, "fragment1 onCreateView end");
        return v;
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
