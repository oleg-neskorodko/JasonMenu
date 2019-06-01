package com.foodapp.jasonmenu;


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

import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment {

    private TextView timeTimerTextView;
    //private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
    private int time;
    private int seconds;
    private ItemClickListener listener;
    private final int STATUS_NONE = 0;
    private final int REFRESH = 1;
    private boolean handlerON;


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
        handlerON = true;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            time = bundle.getInt("time");
        }
        Log.d(MainActivity.TAG, "TimerFragment time = " + time);

        seconds = time * 60;


        return v;
    }

    public static String getStringWithMillis(int millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis)
                        % TimeUnit.HOURS.toHours(1),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                % TimeUnit.MINUTES.toSeconds(1));
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
                        //timeTimerTextView.setText(sdf1.format(seconds * 1000L));
                        timeTimerTextView.setText(getStringWithMillis(seconds*1000));
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
                        if (!handlerON) break;
                        TimeUnit.MILLISECONDS.sleep(1000);
                        handler.sendEmptyMessage(REFRESH);
                    }
                    if (handlerON) {
                        listener.onTimerOut();
                    }
                    handlerON = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        handlerON = false;
    }
}
