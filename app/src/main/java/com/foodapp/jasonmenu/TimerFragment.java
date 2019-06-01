package com.foodapp.jasonmenu;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment {

    private TextView timeTimerTextView;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
    private int time;
    private int seconds;
    private ItemClickListener listener;
    private final int STATUS_NONE = 0;
    private final int REFRESH = 1;


    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.timer_layout, null);
        Log.d(MainActivity.TAG, "TimerFragment onCreateView");

        timeTimerTextView = v.findViewById(R.id.timeTimerTextView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            time = bundle.getInt("time");
        }
        Log.d(MainActivity.TAG, "TimerFragment time = " + time);

        seconds = time * 60;
        timeTimerTextView.setText(sdf1.format(seconds * 1000L));


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        final Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        break;
                    case REFRESH:
                        timeTimerTextView.setText(sdf1.format(seconds * 1000L));
                        seconds--;
                        break;
                }
            }
        };
        handler.sendEmptyMessage(STATUS_NONE);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < time * 60; i++) {
                        TimeUnit.MILLISECONDS.sleep(1000);
                        handler.sendEmptyMessage(REFRESH);
                    }
                    listener.onTimerOut();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();

    }
}
