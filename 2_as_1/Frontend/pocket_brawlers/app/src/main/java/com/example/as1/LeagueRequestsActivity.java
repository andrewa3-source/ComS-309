package com.example.as1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Drew Kinneer
 * @author Jack Kelley
 * The LeagueActivity class in an Android app sets up UI elements and listeners, retrieves and displays
 * league information, and allows for adding league members.
 */
public class LeagueRequestsActivity extends AppCompatActivity {
    Button backButton;
    Button acceptButton;
    Button declineButton;
    EditText usernameRequestInput;
    ListView requestListView;
    TextView leagueTitle;
    String[] requestList;
    JSONArray responses;
    Intent intent;
    private String TAG = AccountActivity.class.getSimpleName();
    // This tag will be used to cancel the request
    private String tag_json_obj = "jobj_req";

    /**
     * This is the onCreate function of a LeagueActivity in an Android app that sets up various UI
     * elements and listeners, and calls functions to retrieve and display league information.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state. This
     * parameter is typically used to restore the activity's state after it has been destroyed and
     * recreated, such as during a configuration change (e.g. screen rotation).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_requests);
        Bundle extras = getIntent().getExtras();
        intent = getIntent();

        backButton = findViewById(R.id.backBtn);
        leagueTitle = findViewById(R.id.leagueId);
        usernameRequestInput = findViewById(R.id.usernameRequestInput);
        acceptButton = findViewById(R.id.acceptBtn);
        declineButton = findViewById(R.id.declineBtn);

        leagueTitle.setText("League " + extras.get("league_id"));
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts a new activity called SignedInActivity with some extra data passed
             * through an intent when a view is clicked.
             *
             * @param v v is a reference to the View object that was clicked by the user. It is passed
             * as a parameter to the onClick() method.
             */
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LeagueRequestsActivity.this, SignedInActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts a new activity called SignedInActivity with some extra data passed
             * through an intent when a view is clicked.
             *
             * @param v v is a reference to the View object that was clicked by the user. It is passed
             * as a parameter to the onClick() method.
             */
            @Override
            public void onClick(View v) {
                int userId;
                for(int i=0; i < responses.length(); ++i){
                    try {
                        String username = responses.getJSONObject(i).getString("username");
                        if (username.equals(usernameRequestInput.getText().toString())){
                            userId = responses.getJSONObject(i).getInt("account_id");
                            requestResponse(userId, true);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This function starts a new activity called SignedInActivity with some extra data passed
             * through an intent when a view is clicked.
             *
             * @param v v is a reference to the View object that was clicked by the user. It is passed
             * as a parameter to the onClick() method.
             */
            @Override
            public void onClick(View v) {
                int userId;
                for(int i=0; i < responses.length(); ++i){
                    try {
                        String username = responses.getJSONObject(i).getString("username");
                        if (username.equals(usernameRequestInput.getText().toString())){
                            userId = responses.getJSONObject(i).getInt("account_id");
                            requestResponse(userId, false);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        getLeagueRequests(this);
    }

    /**
     * This function sends a POST request to a specified URL with a league ID parameter, retrieves a
     * JSON response containing a list of league members, converts the response to a string array, and
     * displays the array in a ListView.
     *
     * @param context The context parameter is an object that provides access to application-specific
     * resources and classes, as well as information about the application environment. It is typically
     * used to create UI elements, access system services, and perform other operations related to the
     * application's lifecycle. In this case, it is used to create an ArrayAdapter
     */
    private void getLeagueRequests(Context context) {
        String uri = Const.URL_ACCOUNTS + "/getAllLeagueRequests";

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                uri, null,
                new Response.Listener<JSONArray>() {

                    /**
                     * This function retrieves a JSON response, parses it to extract an array of league
                     * members, creates an ArrayAdapter to display the members in a ListView, and hides
                     * a progress dialog.
                     *
                     * @param response A JSONObject that contains the response data from a network
                     * request.
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        responses = response;
                        try{
                            String[] returnArray = new String[response.length()];
                            for (int i=0; i<response.length(); ++i){
                                JSONObject responseArrayObject = response.getJSONObject(i);
                                returnArray[i] = responseArrayObject.getString("username");
                            }
                            requestList = returnArray;

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(context,
                                R.layout.list_view, requestList);

                        requestListView = findViewById(R.id.memberList);
                        requestListView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {

            /**
             * This function handles errors in Volley requests and logs the error message while hiding
             * the progress dialog.
             *
             * @param error The "error" parameter is an instance of the VolleyError class, which
             * represents an error that occurred during a Volley request. It contains information about
             * the error, such as the error message and any associated network response. In this
             * method, the error message is logged using VolleyLog and printed to the console
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        }) {
            /**
             * This function returns a map of headers with the "Content-Type" set to
             * "application/json".
             *
             * @return A HashMap containing the header key-value pairs with "Content-Type" set to
             * "application/json".
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonArrReq,
                tag_json_obj);
    }

    private void requestResponse(int userId, Boolean accepted) {
        String uri = Const.URL_ACCOUNTS + "/leagueRequestResponse/" + userId + "/" + accepted;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                uri, null,
                new Response.Listener<JSONObject>() {

                    /**
                     * This function receives a JSON response, extracts an array from it, converts it
                     * to a string array, and sets it as the data source for a ListView adapter.
                     *
                     * @param response A JSONObject that contains the response data from an API call.
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

            /**
             * This function handles errors in Volley requests and logs the error message while hiding
             * the progress dialog.
             *
             * @param error The "error" parameter is an instance of the VolleyError class, which
             * represents an error that occurred during a Volley request. It contains information about
             * the error, such as the error message and any associated network response. In this
             * method, the error message is logged using VolleyLog and printed to the console
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
                startActivity(intent);
            }
        }) {

            /**
             * This function returns a map of headers with the "Content-Type" set to
             * "application/json".
             *
             * @return A HashMap containing the header key-value pairs with "Content-Type" set to
             * "application/json".
             */
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
