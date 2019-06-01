package com.foodapp.jasonmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    public static String TAG = "tag";
    private MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity onCreate");
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity setContentView");

        menuFragment = new MenuFragment();
        menuFragment.setListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuFragment, "menu_fragment").addToBackStack("main_stack").commit();
        } else {
            MenuFragment fragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("menu_fragment");
            if (fragment != null) {
                fragment.setListener(this);
            }
        }
        Log.d(TAG, "MainActivity onCreate end");
    }

    @Override
    public void onItemClick(Bundle bundle) {
        FoodsFragment foodsFragment = new FoodsFragment();
        foodsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, foodsFragment, "foodsFragment").addToBackStack("mainStack").commit();
    }

    @Override
    public void onOrderClick() {
        Log.d(TAG, "MainActivity onOrderClick");
        OrderFragment orderFragment = new OrderFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, orderFragment, "orderFragment").addToBackStack("mainStack").commit();
    }
}
