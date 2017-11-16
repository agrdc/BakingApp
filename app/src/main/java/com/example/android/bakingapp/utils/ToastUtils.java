package com.example.android.bakingapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lsitec207.neto on 13/11/17.
 */

public class ToastUtils {

    private static Toast mToast;

    public ToastUtils() {

    }

    public static void createToast(Context context, String message, int length) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context,message,length);
        mToast.show();
    }

}