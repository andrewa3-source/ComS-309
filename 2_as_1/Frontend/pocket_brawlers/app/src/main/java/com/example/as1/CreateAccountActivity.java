package com.example.as1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
 * The CreateAccountActivity class is responsible for creating a new user account by sending a JSON
 * object request to a server and handling the response.
 * @author Jack Kelley
 */
public class CreateAccountActivity extends AppCompatActivity {
    Button backBtn;
    Button createAccountBtn;
    EditText usernameInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    private String TAG = AccountActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    // This tag will be used to cancel the request
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        backBtn = findViewById(R.id.backBtn);
        createAccountBtn = findViewById(R.id.createAccountBtn);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreateAccountActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Check that the password was confirmed correctly
                if(passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())){
                    makeJsonObjReq();
                }
            }
        });
    }

    /**
     * This function shows a progress dialog if it is not already showing.
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * This function hides a progress dialog if it is currently showing.
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json object request
     **/
    private void makeJsonObjReq() {
        showProgressDialog();
        String uri = Const.URL_ACCOUNTS + "/createAccount";

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", usernameInput.getText().toString());
        params.put("password", passwordInput.getText().toString());
        params.put("type", "0");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                uri, new JSONObject(params),
                response -> {
                    Log.d(TAG, response.toString());
                    try{
                        //Navigate back to main activity
                        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                        intent.putExtra("username",  response.get("username").toString());
                        intent.putExtra("password", response.get("password").toString());
                        intent.putExtra("accountType", Integer.parseInt(response.get("type").toString()));
                        intent.putExtra("signedIn", true);
                        intent.putExtra("id", response.getInt("id"));
                        startActivity(intent);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    hideProgressDialog();
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
                hideProgressDialog();
            }
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

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}
