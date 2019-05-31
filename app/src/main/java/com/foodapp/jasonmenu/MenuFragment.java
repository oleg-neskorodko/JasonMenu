package com.foodapp.jasonmenu;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {

    private ApiService apiService;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MenuAdapter adapter;
    private ItemClickListener listener;
    private List<MenuModel> posts;
    private ArrayList<String> posts2;

    private TextView headerTextView;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");


    public void setListener(ItemClickListener listener) {
        Log.d(MainActivity.TAG, "MenuFragment listener set!");
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_layout, null);
        Log.d(MainActivity.TAG, "fragment1 onCreateView");

        headerTextView = v.findViewById(R.id.headerTextView);

        posts = new ArrayList<>();

        if (posts == null) {
            Log.d(MainActivity.TAG, "posts NULL");
        } else Log.d(MainActivity.TAG, "posts NOT null");

        posts2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            posts2.add(i, "element" + i);
        }


        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MenuAdapter(getActivity(), posts);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView.addItemDecoration(new SpaceItemDecoration(20)); //todo

        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                Log.d(MainActivity.TAG, "onClick position = " + position);
                //Bundle bundle1 = new Bundle();
                //listener.onItemClick(bundle1);
            }
        });

        Log.d(MainActivity.TAG, "fragment1 onCreateView end");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, "MenuFragment onResume");

        headerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.headerTextView:
                        Log.d(MainActivity.TAG, "TextView onClick");
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("bundle_size", 6);
                        for (int i = 0; i < 6; i++) {
                            bundle1.putString("first" + i, "firstdata" + i);
                            bundle1.putString("second" + i, "seconddata" + i);
                        }

                        listener.onTextClick(bundle1);
                        break;
                }

            }
        });

        requestData();
        headerTextView.setText(getActivity().getResources().getString(R.string.menu_date) + sdf1.format(System.currentTimeMillis()));
    }

    private void requestData() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        Log.d(MainActivity.TAG, "retrofit created");

        apiService.getMenu(Constants.LINK3).enqueue(new Callback<JsonElement>() {


            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d(MainActivity.TAG, "onResponse = " + response.body().toString());

                Gson gson = new Gson();
                Type listType = new TypeToken<List<MenuModel>>(){}.getType();
                posts.clear();
                posts = gson.fromJson(response.body().toString(), listType);
                Log.d(MainActivity.TAG, "Gson converter = " + posts.get(0).getName());
                Log.d(MainActivity.TAG, "Gson converter = " + posts.size());
                recyclerView.getAdapter().notifyDataSetChanged();
                Log.d(MainActivity.TAG, "notifyDataSetChanged");

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(MainActivity.TAG, "request1 onFailure");
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "this is an actual network failure", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

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
