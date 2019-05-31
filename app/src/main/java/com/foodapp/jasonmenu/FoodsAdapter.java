package com.foodapp.jasonmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder>{

    private Context context1;
    private List<String> symbolList;
    private View.OnClickListener clickListener;
    private Bundle bundle1;


    public FoodsAdapter(Context context1, Bundle bundle1) {
        this.context1 = context1;
        this.bundle1 = bundle1;
        for (int i = 0; i < 6; i++) {
            Log.d(MainActivity.TAG, bundle1.getString("first" + i));
            Log.d(MainActivity.TAG, bundle1.getString("second" + i));
        }

    }

/*    public void setClickListener(View.OnClickListener callback) {
        clickListener = callback;
    }*/

    @Override
    public FoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(MainActivity.TAG, "onCreateViewHolder post size = " + getItemCount());


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods,
                parent, false);

        FoodsAdapter.ViewHolder holder = new FoodsAdapter.ViewHolder(v);
/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(FoodsAdapter.ViewHolder holder, int position) {
        Log.d(MainActivity.TAG, "FoodsAdapter onBindViewHolder = " + getItemCount());


        holder.textView1.setText(bundle1.getString("first" + position));
        holder.textView2.setText(bundle1.getString("second" + position));



    }

    @Override
    public int getItemCount() {
        if (bundle1 == null)
            return 0;
        return bundle1.getInt("bundle_size");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;



        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }



}
