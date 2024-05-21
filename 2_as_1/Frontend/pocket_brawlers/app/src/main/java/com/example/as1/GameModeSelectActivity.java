package com.example.as1;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Kelley
 * The GameModeSelectActivity class sets up button listeners for a game mode selection activity in an
 * Android app and launches a BrawlerActivity with different parameters depending on which button is
 * clicked.
 */
public class GameModeSelectActivity extends AppCompatActivity {

    Button multiplayerButton;
    Button singleplayerButton;
    Button chatButton;
    String username = "User";
    Button backButton;

    ImageView profilePicture;

    private String tag_json_obj = "jobj_req";

    /**
     * The onCreate function sets up the layout and button listeners for a game mode selection activity
     * in an Android app, and launches a BrawlerActivity with different parameters depending on which
     * button is clicked.
     *
     * @param savedInstanceState savedInstanceState is a Bundle object that contains the previous state
     *                           of the activity, which can be used to restore the activity to its previous state if it was
     *                           destroyed and recreated by the system. It is typically used to store and retrieve data that is
     *                           not persistent, such as the current state of the UI.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemodeselect);

        singleplayerButton = findViewById(R.id.singleplayerBtn);
        multiplayerButton = findViewById(R.id.multiplayerBtn);
        chatButton = findViewById(R.id.chatBtn);
        backButton = findViewById(R.id.backBtn);
        profilePicture = findViewById(R.id.profilePicture);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle extras = getIntent().getExtras();

        TextView messageTextView = (TextView) findViewById(R.id.username);
        makeImageRequest();
        username = extras.getString("username");
        messageTextView.setText("Hey, " + username);

        singleplayerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts a new activity called BrawlerActivity with some extras and a
             * boolean value indicating whether to use websockets or not.
             *
             * @param v v is a View object that represents the view that was clicked by the user. In
             * this case, it is the view that triggers the onClick() method.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameModeSelectActivity.this, BrawlerActivity.class);
                intent.putExtras(extras);
                intent.putExtra("useWebsockets", false);
                resetTurn();
                startActivity(intent);
            }
        });

        multiplayerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts a new activity called BrawlerActivity with extras and a boolean
             * value indicating the use of websockets.
             *
             * @param v v is a View object that represents the view that was clicked by the user. In
             * this case, it is the view that triggers the onClick() method.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameModeSelectActivity.this, BrawlerActivity.class);
                intent.putExtras(extras);
                intent.putExtra("useWebsockets", true);
                resetTurn();
                startActivity(intent);
            }
        });

        chatButton.setOnClickListener(view -> {
            Intent intent;
            intent = new Intent(GameModeSelectActivity.this, ChatActivity.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(GameModeSelectActivity.this, MainActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        });
    }

    private void resetTurn() {
        String uri = Const.URL_ACCOUNTS + "/resetTurn/" + getIntent().getExtras().get("id");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                uri, null,
                response -> {
                }, error -> {
            Log.d(TAG, "Error: " + error.getMessage());
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }

    private void makeImageRequest() {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // Loading image with placeholder and error image
        imageLoader.get(Const.URL_IMAGE, ImageLoader.getImageListener(
                profilePicture, R.drawable.ico_loading, R.drawable.ico_error));

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Const.URL_IMAGE);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}