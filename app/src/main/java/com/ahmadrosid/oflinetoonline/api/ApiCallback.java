package com.ahmadrosid.oflinetoonline.api;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public interface ApiCallback {
    void onResponse(String response);
    void onError(Throwable throwable);
}
