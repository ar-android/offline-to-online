package com.ahmadrosid.oflinetoonline.data;

import android.content.Context;
import android.util.Log;

import com.ahmadrosid.oflinetoonline.api.Api;
import com.ahmadrosid.oflinetoonline.api.ApiCallback;
import com.ahmadrosid.oflinetoonline.helper.NetworkHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public class PostManager {
    private static final String TAG = "PostManager";
    private static PostManager INSTANCE;

    public static PostManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PostManager();
        return INSTANCE;
    }

    public void sendPost(final Context context) {
        Log.d(TAG, "sendPost: true");
        if (NetworkHelper.isConnectedToInternet(context)) {
            final Realm realm = Realm.getDefaultInstance();
            long data = realm.where(Post.class).count();
            if (data != 0) {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override public void execute(Realm realm) {
                        List<Post> data = realm.where(Post.class).findAll();
                        for (Post post : data) {
                            executePost(post.getName());
                        }
                        realm.where(Post.class).findAll().deleteAllFromRealm();
                    }
                });
                realm.close();
            }
        }
    }

    private void executePost(String name) {
        Api.post(name, new ApiCallback() {
            @Override public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final String message = jsonObject.getString("message");
                    if (jsonObject.getBoolean("success")) {
                        Log.d(TAG, "onResponse: " + message);
                    }
                } catch (JSONException e) {
                    onError(e);
                }
            }

            @Override public void onError(Throwable throwable) {
                Log.e(TAG, "onError: ", throwable);
            }
        });
    }
}
