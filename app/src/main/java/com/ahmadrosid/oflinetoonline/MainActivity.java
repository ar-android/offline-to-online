package com.ahmadrosid.oflinetoonline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmadrosid.oflinetoonline.api.Api;
import com.ahmadrosid.oflinetoonline.api.ApiCallback;
import com.ahmadrosid.oflinetoonline.data.Post;
import com.ahmadrosid.oflinetoonline.data.PostManager;
import com.ahmadrosid.oflinetoonline.helper.NetworkHelper;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
    }

    @Override protected void onResume() {
        super.onResume();
        PostManager.getInstance().sendPost(this);
    }

    public void clickSendPost(View view) {
        final EditText name = (EditText) findViewById(R.id.input_name);
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please input form name", Toast.LENGTH_SHORT).show();
        } else {
            if (NetworkHelper.isConnectedToInternet(this)) {
                Api.post(name.getText().toString(), new ApiCallback() {
                    @Override public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            response(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override public void onError(Throwable throwable) {
                        Log.e(TAG, "onError: ", throwable);
                        response(throwable.getMessage());
                    }
                });
            }else{
                realm.executeTransaction(new Realm.Transaction() {
                    @Override public void execute(Realm realm) {
                        Post post = realm.createObject(Post.class);
                        post.setName(name.getText().toString());
                    }
                });

                Log.d(TAG, "Number of post: " + realm.where(Post.class).count());
            }
        }
    }

    private void response(final String response) {
        this.runOnUiThread(new Runnable() {
            @Override public void run() {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
