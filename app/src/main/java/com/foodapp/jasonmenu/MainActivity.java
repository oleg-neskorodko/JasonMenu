package com.foodapp.jasonmenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    public static String TAG = "tag";
    private MenuFragment menuFragment;
    private OrderFragment orderFragment;
    private FoodsFragment foodsFragment;
    private TimerFragment timerFragment;
    private SharedPreferences sPref;

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
        foodsFragment = new FoodsFragment();
        foodsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, foodsFragment, "foodsFragment").addToBackStack("mainStack").commit();
    }

    @Override
    public void onOrderClick() {
        Log.d(TAG, "MainActivity onOrderClick");
        orderFragment = new OrderFragment();
        orderFragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, orderFragment, "orderFragment").addToBackStack("mainStack").commit();
    }

    @Override
    public void onConfirmButtonClick(int time) {
        Log.d(TAG, "MainActivity onConfirmButtonClick");
        timerFragment = new TimerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("time", time);
        timerFragment.setArguments(bundle);
        timerFragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, timerFragment, "timerFragment").addToBackStack("mainStack").commit();
    }

    @Override
    public void onTimerOut() {
        sPref = getSharedPreferences("Order", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("order", "[]");
        ed.commit();
        menuFragment = new MenuFragment();
        menuFragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, menuFragment, "menu_fragment").addToBackStack("main_stack").commit();
    }

}
