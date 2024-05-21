package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Kelley
 * The SignedInActivity class sets the content view based on the account type and handles button clicks
 * to navigate to other activities.
 */
public class SignedInActivity extends AppCompatActivity {
    Button backButton;
    Button leagueButton;
    Button signOutButton;
    Button leagueRequestButton;
    Button leagueOrganizerRequestButton;
    Button changePasswordButton;
    String usernameVal = "User";
    String passwordVal = "";
    Boolean signedIn = false;
    int accountType;
    int wins;
    int loss;
    TextView winLoss;
    TextView leagueText;
    TextView username;
    TextView password;
    Bundle extras;
    private String TAG = AccountActivity.class.getSimpleName();
    // This tag will be used to cancel the request
    private String tag_json_obj = "jobj_req";

    /**
     * This function sets the content view based on the account type and handles button clicks to
     * navigate to other activities.
     *
     * @param savedInstanceState savedInstanceState is a Bundle object that contains the data that was
     * saved in the previous state of the activity, which can be used to restore the activity to its
     * previous state if it was destroyed and recreated by the system.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        usernameVal = extras.getString("username");
        passwordVal = extras.getString("password");
        signedIn = extras.getBoolean("signedIn");
        accountType = extras.getInt("accountType");
        getDisplayValues(usernameVal, passwordVal);
        try {
            Thread.sleep(500); //wait for server
        } catch (InterruptedException e) {
        }
        new CountDownTimer(300, 300) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                renderActivity(extras);
            }
        }.start();
        switch(accountType) {
            case 0:
                setContentView(R.layout.activity_signedin_user);
                leagueOrganizerRequestButton = findViewById(R.id.adminRequestBtn);
                leagueOrganizerRequestButton.setOnClickListener(view -> {
                    sendLeagueOrganizerRequest(extras.getInt("id"));
                });
                if (extras.getInt("league_id") != 0){
                    leagueButton = findViewById(R.id.leagueBtn);
                    leagueText = findViewById(R.id.leagueTitle);
                    leagueButton.setVisibility(View.VISIBLE);
                    leagueText.setVisibility(View.VISIBLE);

                    leagueButton.setOnClickListener(new View.OnClickListener() {
                        /**
                         * This function starts a new activity called LeagueActivity with some extra data
                         * passed through an intent.
                         *
                         * @param view The view parameter in this code refers to the view that was clicked
                         * by the user, which triggered the onClick() method to be called. It is passed as
                         * an argument to the method automatically by the Android framework when the user
                         * interacts with the UI.
                         */
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SignedInActivity.this, LeagueActivity.class);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case 1:
                setContentView(R.layout.activity_signedin_admin);
                if (extras.getInt("league_id") != 0){
                    leagueButton = findViewById(R.id.leagueBtn);
                    leagueText = findViewById(R.id.leagueTitle);
                    leagueButton.setVisibility(View.VISIBLE);
                    leagueText.setVisibility(View.VISIBLE);

                    leagueButton.setOnClickListener(new View.OnClickListener() {
                        /**
                         * This function starts a new activity called LeagueActivity with some extra data
                         * passed through an intent.
                         *
                         * @param view The view parameter in this code refers to the view that was clicked
                         * by the user, which triggered the onClick() method to be called. It is passed as
                         * an argument to the method automatically by the Android framework when the user
                         * interacts with the UI.
                         */
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SignedInActivity.this, LeagueActivity.class);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case 2:
                setContentView(R.layout.activity_signedin_dev);
                leagueRequestButton = findViewById(R.id.leagueRequestBtn);
                leagueRequestButton.setOnClickListener(new View.OnClickListener() {
                    /**
                     * This function starts a new activity called LeagueActivity with some extra data
                     * passed through an intent.
                     *
                     * @param view The view parameter in this code refers to the view that was clicked
                     * by the user, which triggered the onClick() method to be called. It is passed as
                     * an argument to the method automatically by the Android framework when the user
                     * interacts with the UI.
                     */
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SignedInActivity.this, LeagueRequestsActivity.class);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                });
                if (extras.getInt("league_id") != 0){
                    leagueButton = findViewById(R.id.leagueBtn);
                    leagueText = findViewById(R.id.leagueTitle);
                    leagueButton.setVisibility(View.VISIBLE);
                    leagueText.setVisibility(View.VISIBLE);

                    leagueButton.setOnClickListener(new View.OnClickListener() {
                        /**
                         * This function starts a new activity called LeagueActivity with some extra data
                         * passed through an intent.
                         *
                         * @param view The view parameter in this code refers to the view that was clicked
                         * by the user, which triggered the onClick() method to be called. It is passed as
                         * an argument to the method automatically by the Android framework when the user
                         * interacts with the UI.
                         */
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SignedInActivity.this, LeagueActivity.class);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    });
                }
                break;
        }
    }

    private void renderActivity(Bundle extras){
        backButton = findViewById(R.id.backBtn);

        winLoss = findViewById(R.id.winLossVal);
        username = findViewById(R.id.usernameVal);
        password = findViewById(R.id.passwordVal);
        signOutButton = findViewById(R.id.signoutBtn);
        changePasswordButton = findViewById(R.id.changePasswordBtn);

        String winLossText = wins + "/" + loss;
        winLoss.setText(winLossText);
        username.setText(usernameVal);

        StringBuilder passwordAsterisks = new StringBuilder();
        for (int i = 0; i < passwordVal.length(); i++){
            passwordAsterisks.append("*");
        }

        password.setText(passwordAsterisks.toString());

        signOutButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignedInActivity.this, MainActivity.class);
            startActivity(intent);
        });

        changePasswordButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignedInActivity.this, ChangePasswordActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts a new activity when a button is clicked, passing along any extras.
             *
             * @param v v is a View object that represents the view that was clicked by the user. It is
             * passed as a parameter to the onClick() method of a View.OnClickListener interface. In
             * this case, it is used to trigger the start of a new activity when the user clicks on a
             * button or view.
             */
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignedInActivity.this, MainActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void getDisplayValues(String username, String password) {
        String uri = Const.URL_ACCOUNTS + "/authorizeAccount";

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                uri, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try{
                            wins = response.getInt("wins");
                            loss = response.getInt("loss");
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        }) {
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

    private void sendLeagueOrganizerRequest(int id) {
        String uri = Const.URL_ACCOUNTS + "/requestLeagueOrganizer/" + id;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                uri, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Intent intent = new Intent(SignedInActivity.this, SignedInActivity.class);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        }) {
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
}
