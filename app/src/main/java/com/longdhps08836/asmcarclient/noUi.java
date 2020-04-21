package com.longdhps08836.asmcarclient;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class noUi {

    public Context context;

    public noUi(Context context) {
        this.context = context;
    }

    public void toast(final String text) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateListView() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Fragment_Profile_Client.updateListAdapter();
            }
        });
    }

    public void updateListCarProduct() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Fragment_Home.updateListAdapterCarProduct();
            }
        });
    }
}
