package com.foodapp.jasonmenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context1;
    private List<MenuModel> posts;
    private View.OnClickListener clickListener;

    public MenuAdapter(Context context1, List<MenuModel> posts) {
        Log.d(MainActivity.TAG, "MenuAdapter constructor");
        this.context1 = context1;
        this.posts = posts;
    }

    public void setClickListener(View.OnClickListener callback) {
        clickListener = callback;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(MainActivity.TAG, "onCreateViewHolder post size = " + getItemCount());


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,
                parent, false);

        MenuAdapter.ViewHolder holder = new MenuAdapter.ViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        Log.d(MainActivity.TAG, "onBindViewHolder post size = " + getItemCount());



        holder.nameMenuTextView.setText(posts.get(position).getName());
        holder.numberMenuTextView.setText(String.valueOf(posts.get(position).getFoods().size()));
        holder.menuImageView.setImageDrawable(context1.getDrawable(R.drawable.salad02));

    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameMenuTextView;
        TextView numberMenuTextView;
        ImageView menuImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameMenuTextView = itemView.findViewById(R.id.nameMenuTextView);
            numberMenuTextView = itemView.findViewById(R.id.numberMenuTextView);
            menuImageView = itemView.findViewById(R.id.menuImageView);
        }
    }
}