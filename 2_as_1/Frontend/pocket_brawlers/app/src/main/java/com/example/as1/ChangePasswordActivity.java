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
public class ChangePasswordActivity extends AppCompatActivity {
    Button backBtn;
    Button changePasswordBtn;
    EditText passwordInput;
    EditText newPasswordInput;
    EditText confirmNewPasswordInput;
    private String TAG = AccountActivity.class.getSimpleName();
    // This tag will be used to cancel the request
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Bundle extras = getIntent().getExtras();

        backBtn = findViewById(R.id.backBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        passwordInput = findViewById(R.id.passwordInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmNewPasswordInput = findViewById(R.id.confirmNewPasswordInput);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ChangePasswordActivity.this, SignedInActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Check that the password was confirmed correctly
                if(newPasswordInput.getText().toString().equals(confirmNewPasswordInput.getText().toString()) && passwordInput.getText().toString().equals(getIntent().getExtras().get("password"))){
                    updatePassword();
                    Intent intent = new Intent(ChangePasswordActivity.this, SignedInActivity.class);
                    intent.putExtras(extras);
                    intent.removeExtra("password");
                    intent.putExtra("password", newPasswordInput.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void updatePassword() {
        String uri = Const.URL_ACCOUNTS + "/changePassword/";
        Bundle extras = getIntent().getExtras();

        Map<String, String> params = new HashMap<String, String>();
        params.put("newPassword", newPasswordInput.getText().toString());
        params.put("id", extras.get("id").toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                uri, new JSONObject(params),
                response -> {
                    Log.d(TAG, response.toString());
                    try{
                        //Navigate back to main activity
                        Intent intent = new Intent(ChangePasswordActivity.this, SignedInActivity.class);
                        intent.putExtras(extras);
                        intent.removeExtra("password");
                        intent.putExtra("password", response.getString("password"));
                        startActivity(intent);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
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
