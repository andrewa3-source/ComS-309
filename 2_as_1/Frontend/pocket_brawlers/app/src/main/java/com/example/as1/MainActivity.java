package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.toolbox.ImageLoader;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Jack Kelley
 * @author Drew Kinneer
 * The MainActivity class sets up the UI and handles button clicks for navigating to other activities
 * in an Android app.
 */
public class MainActivity extends AppCompatActivity {

    Button brawlersButton;
    Button accountButton;
    Button chatButton;
    String username = "User";
    Boolean signedIn = false;
    ImageView profilePicture;

    /**
     * This is the onCreate function of the main activity in an Android app that sets up the UI and
     * handles button clicks for navigating to other activities.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state. It
     *                           can be null if there is no saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        brawlersButton = findViewById(R.id.toBrawlersBtn);
        accountButton = findViewById(R.id.signInBtn);
        chatButton = findViewById(R.id.chatBtn);
        profilePicture = findViewById(R.id.profilePicture);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            signedIn = extras.getBoolean("signedIn");
        }

        TextView messageTextView = (TextView) findViewById(R.id.username);
        messageTextView.setText("Not signed in");
        if (signedIn) {
            messageTextView.setText("Hey, " + username);
            accountButton.setText("Profile");
            makeImageRequest();
        } else {
            brawlersButton.setVisibility(View.GONE);
            chatButton.setVisibility(View.GONE);
        }

        brawlersButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts a new activity called GameModeSelectActivity when a button is
             * clicked in the MainActivity and passes any extras from the current intent to the new
             * intent.
             *
             * @param v v is a parameter of the onClick method, which is a reference to the View that
             * was clicked. It represents the UI element that triggered the click event.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameModeSelectActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts an activity based on whether the user is signed in or not.
             *
             * @param v v is a parameter of the onClick method, which represents the View that was
             * clicked by the user. It is typically used to determine which button or element was
             * clicked and perform the appropriate action.
             */
            @Override
            public void onClick(View v) {
                Intent intent;
                if (signedIn) {
                    intent = new Intent(MainActivity.this, SignedInActivity.class);
                    intent.putExtras(getIntent().getExtras());
                } else {
                    intent = new Intent(MainActivity.this, AccountActivity.class);
                }
                startActivity(intent);
            }
        });

        chatButton.setOnClickListener(view -> {
            Intent intent;
            intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
        });
    }

    /**
     * The function makes an image request using an image loader and checks if the response is cached
     * before making a network call.
     */
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