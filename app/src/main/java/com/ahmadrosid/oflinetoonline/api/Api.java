package com.ahmadrosid.oflinetoonline.api;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public class Api {

    public static void post(String name, final ApiCallback apiCallback){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "name=" + name);
        Request request = new Request.Builder()
                .url("http://172.20.10.2/post")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                apiCallback.onError(e);
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                apiCallback.onResponse(response.body().string());
            }
        });
    }
}
