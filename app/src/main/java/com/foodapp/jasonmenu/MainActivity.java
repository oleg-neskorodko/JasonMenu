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
        getSupportActionBar().hide();

        Log.d(TAG, "MainActivity setContentView");

        menuFragment = new MenuFragment();
        menuFragment.setListener(this);
        if (savedInstanceState == null) {
            //listFragment.setArguments(mainDataSet);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuFragment, "menu_fragment").addToBackStack("main_stack").commit();
            //Log.d(TAG, "MA fragment launch");
        } else {
            //mainDataSet = savedInstanceState.getBundle("mainDataSet");
            MenuFragment fragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("menu_fragment");
            if (fragment != null) {
                fragment.setListener(this);
            }
        }
        Log.d(TAG, "MainActivity onCreate end");
    }

    @Override
    public void onItemClick(String str) {

    }

    @Override
    public void onTextClick(Bundle bundle) {
        FoodsFragment foodsFragment = new FoodsFragment();
        foodsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, foodsFragment, "foodsFragment").addToBackStack("mainStack").commit();
    }
}
