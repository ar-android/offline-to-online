package com.ahmadrosid.oflinetoonline.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ahmadrosid.oflinetoonline.data.PostManager;
import com.ahmadrosid.oflinetoonline.helper.NetworkHelper;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public class ConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG = "ConnectivityReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + NetworkHelper.isConnectedToInternet(context));
        if (NetworkHelper.isConnectedToInternet(context)) {
            PostManager.getInstance().sendPost(context);
        }
    }
}
