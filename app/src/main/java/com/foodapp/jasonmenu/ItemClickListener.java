package com.foodapp.jasonmenu;

import android.os.Bundle;

public interface ItemClickListener {
    void onItemClick(Bundle bundle);
    void onOrderClick();
    void onConfirmButtonClick(int time);
    void onTimerOut();
}
