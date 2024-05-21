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
public class LeagueActivity extends AppCompatActivity {
    Button backButton;
    Button addMemberButton;
    ListView memberListView;
    TextView leagueTitle;
    EditText usernameInput;
    TextView username;
    String[] memberList;
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
        setContentView(R.layout.activity_league);
        Bundle extras = getIntent().getExtras();

        backButton = findViewById(R.id.backBtn);
        addMemberButton = findViewById(R.id.addMemberBtn);
        leagueTitle = findViewById(R.id.leagueId);
        usernameInput = findViewById(R.id.usernameInput);
        username = findViewById(R.id.textView6);
        if (extras.getInt("accountType") == 0){
            addMemberButton.setVisibility(View.INVISIBLE);
            usernameInput.setVisibility(View.INVISIBLE);
            username.setVisibility(View.INVISIBLE);
        }

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
                Intent intent = new Intent(LeagueActivity.this, SignedInActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This is an onClick listener function that calls the addLeagueMember function with the
             * league_id and context as parameters.
             *
             * @param v v is a View object that represents the view that was clicked by the user. In
             * this case, it is the view that triggered the onClick() method.
             */
            @Override
            public void onClick(View v){
                addLeagueMember(""+extras.getInt("league_id"), LeagueActivity.super.getBaseContext());
            }
        });

        getLeague(""+extras.getInt("league_id"), this);
    }

    /**
     * This function sends a POST request to a specified URL with a league ID parameter, retrieves a
     * JSON response containing a list of league members, converts the response to a string array, and
     * displays the array in a ListView.
     *
     * @param leagueId The ID of the league that you want to retrieve information for.
     * @param context The context parameter is an object that provides access to application-specific
     * resources and classes, as well as information about the application environment. It is typically
     * used to create UI elements, access system services, and perform other operations related to the
     * application's lifecycle. In this case, it is used to create an ArrayAdapter
     */
    private void getLeague(String leagueId, Context context) {
        String uri = Const.URL_ACCOUNTS + "/getLeague";

        Map<String, String> params = new HashMap<String, String>();
        params.put("league_id", leagueId);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                uri, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    /**
                     * This function retrieves a JSON response, parses it to extract an array of league
                     * members, creates an ArrayAdapter to display the members in a ListView, and hides
                     * a progress dialog.
                     *
                     * @param response A JSONObject that contains the response data from a network
                     * request.
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try{
                            JSONArray responseArray = response.getJSONArray("league_members");
                            String[] returnArray = new String[responseArray.length()];
                            for (int i=0; i<responseArray.length(); ++i){
                                returnArray[i] = responseArray.getString(i);
                            }
                            memberList = returnArray;

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(context,
                                R.layout.list_view, memberList);

                        memberListView = findViewById(R.id.memberList);
                        memberListView.setAdapter(adapter);
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
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }

    /**
     * This function adds a league member to a specified league and updates the member list displayed
     * in the UI.
     *
     * @param leagueId The ID of the league to which a member is being added.
     * @param context The context parameter is an object that provides access to application-specific
     * resources and classes, as well as information about the application environment. It is typically
     * used to create UI elements, access system services, and perform other operations related to the
     * application's lifecycle. In this specific code snippet, the context parameter is used
     */
    private void addLeagueMember(String leagueId, Context context) {
        String uri = Const.URL_ACCOUNTS + "/addLeagueMember";

        Map<String, String> params = new HashMap<String, String>();
        params.put("league_id", leagueId);
        params.put("username", usernameInput.getText().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                uri, new JSONObject(params),
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
                        try{
                            JSONArray responseArray = response.getJSONArray("league_members");
                            String[] returnArray = new String[responseArray.length()];
                            for (int i=0; i<responseArray.length(); ++i){
                                returnArray[i] = responseArray.getString(i);
                            }
                            memberList = returnArray;
                            ArrayAdapter adapter = new ArrayAdapter<String>(context,
                                    R.layout.list_view, memberList);

                            memberListView = findViewById(R.id.memberList);
                            memberListView.setAdapter(adapter);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
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
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }
}
