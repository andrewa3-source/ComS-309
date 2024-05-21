package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as1.utils.Const;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Jack Kelley
 * This class sets up a WebSocket connection to a matchmaking server and waits for a message indicating
 * a match has been found, then starts a new activity with the opponent's ID.
 */
public class MultiplayerReadyActivity extends AppCompatActivity {
    ImageView loadingBrawler;

    private WebSocketClient cc;
    /**
     * This function sets up a WebSocket connection to a matchmaking server and waits for a message
     * indicating a match has been found, then starts a new activity with the opponent's ID.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state. It
     * can be null if there is no saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayerready);
        Bundle extras = getIntent().getExtras();

        loadingBrawler = findViewById(R.id.loadingBrawler);
        loadingBrawler.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading_rotate));
        loadingBrawler.getAnimation().start();

        Draft[] drafts = {
                new Draft_6455()
        };

        /**
         * If running this on an android device, make sure it is on the same network as your
         * computer, and change the ip address to that of your computer.
         * If running on the emulator, you can use localhost.
         */
        String w = "ws://coms-309-023.class.las.iastate.edu:8080/websocket/matchmaking/" + getIntent().getExtras().getInt("id");


        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                /**
                 * This function listens for a message and starts a new activity if the message
                 * contains a specific string.
                 *
                 * @param message The message received by the onMessage() method.
                 */
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    if (message.contains("matched with")){
                        Intent intent = new Intent(MultiplayerReadyActivity.this, BattleActivity.class);
                        intent.putExtras(extras);
                        intent.putExtra("opponent_id", Integer.parseInt(message.split("matched with ")[1]));
                        startActivity(intent);
                    }
                }

                /**
                 * This function sends a message "Ready" when a connection is established.
                 *
                 * @param handshake The ServerHandshake parameter in the onOpen method represents the
                 * handshake information received from the server when the WebSocket connection is
                 * established. It contains details such as the HTTP status code, HTTP headers, and
                 * other information related to the WebSocket connection.
                 */
                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    cc.send("Ready");
                }

                /**
                 * This function handles the event when a WebSocket connection is closed and redirects
                 * the user to the game mode selection activity.
                 *
                 * @param code The code parameter in the onClose() method represents the WebSocket
                 * close code that indicates the reason for the connection being closed. It is an
                 * integer value.
                 * @param reason The reason parameter in the onClose() method is a string that
                 * represents the reason for the WebSocket connection being closed. It could be due to
                 * various reasons such as a network error, server error, or the user closing the
                 * connection. The reason string can provide more information about the cause of the
                 * connection closure.
                 * @param remote The "remote" parameter in the onClose() method indicates whether the
                 * WebSocket connection was closed by the remote endpoint (true) or by the local
                 * endpoint (false).
                 */
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                    Intent intent = new Intent(MultiplayerReadyActivity.this, GameModeSelectActivity.class);
                    intent.putExtras(extras);
                    intent.removeExtra("opponent_id");
                    startActivity(intent);
                }

                /**
                 * This is an implementation of the onError method in Java that logs any exceptions
                 * that occur.
                 *
                 * @param e The parameter "e" is an instance of the Exception class, which represents
                 * an error or exceptional condition that has occurred during the execution of a
                 * program. In this case, the method is logging the details of the exception to the
                 * console using the Log.d() method.
                 */
                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
    }
}
