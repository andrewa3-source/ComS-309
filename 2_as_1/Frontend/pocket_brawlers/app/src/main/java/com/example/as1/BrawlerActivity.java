package com.example.as1;

import static com.example.as1.utils.Const.URL_MOCK_DREW;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The BrawlerActivity class is responsible for managing the user interface and functionality of the
 * shop screen.
 * @author Drew Kinneer
 * @author Jack Kelley
 */
public class BrawlerActivity extends AppCompatActivity {
    Button backBtn;
    Button rollButton;

    private String TAG = AccountActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    private String tag_json_obj = "jobj_req";
    Button battleBtn;

    Button sellButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brawler);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle extras = getIntent().getExtras();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        backBtn = findViewById(R.id.backBtn);
        battleBtn = findViewById(R.id.battleBtn);
        sellButton = findViewById(R.id.sellBtn);

        sellButton.setOnDragListener((v, e) -> {
            View view = (View) e.getLocalState();
            int dragEvent = e.getAction();

            int spotIndex = -1;

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true; //Return true to indicate this View can accept drag
                case DragEvent.ACTION_DROP:
                    if (view.getId() == R.id.spot1) {
                        spotIndex = 0;
                    } else if (view.getId() == R.id.spot2) {
                        spotIndex = 1;
                    } else if (view.getId() == R.id.spot3) {
                        spotIndex = 2;
                    } else if (view.getId() == R.id.spot4) {
                        spotIndex = 3;
                    } else if (view.getId() == R.id.spot5) {
                        spotIndex = 4;
                    }

                    Brawler brawler = ActiveLineup.getLineup()[spotIndex].getBrawler();

                    if (spotIndex != -1 && brawler != null) {
                        if (brawler.getBrawlerEffect().getEffectType() == BrawlerEffectType.SELL) {
                            brawler.getBrawlerEffect().doEffect();
                        }
                        ActiveLineup.lineup[spotIndex].clear();
                        PlayerStats.sell();
                        return true;
                    }
            }
            return false;
        });

        rollButton = findViewById(R.id.rollBtn);
        new PlayerStats(this);
        new ActiveLineup(this);
        new ShopLineup(this);
        for (int i = 0; i < 3; i++) {
            setShopBrawlers(i);
        }
        for (int i = 0; i < 2; i++) {
            setShopFoods(i);
        }

        ActiveLineup.setFirstTurn(false);

        backBtn.setOnClickListener(v -> {
            ActiveLineup.setFirstTurn(true);
            Intent intent = new Intent(BrawlerActivity.this, GameModeSelectActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        });

        battleBtn.setOnClickListener(v -> {
            if (!ActiveLineup.isLineupEmpty()) {
                runBattleEffects();
                sendLineupBrawler();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (extras.getBoolean("useWebsockets")) {
                    Intent intent = new Intent(BrawlerActivity.this, MultiplayerReadyActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(BrawlerActivity.this, BattleActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });

        rollButton.setOnClickListener(view -> {
            if (PlayerStats.getDoubloonsVal() >= 1) {
                for (int i = 0; i < 3; i++) {
                    setShopBrawlers(i);
                }
                for (int i = 0; i < 2; i++) {
                    setShopFoods(i);
                }
                PlayerStats.roll();
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

    private void runBattleEffects(){
        for (int i = 0; i < ActiveLineup.getLineup().length; i++) {
            Brawler brawler = ActiveLineup.getBrawler(i);
            if (brawler != null) {
                if (brawler.getBrawlerEffect().getEffectType() == BrawlerEffectType.BATTLE) {
                    brawler.getBrawlerEffect().doEffect();
                }
            }
        }
    }

    /**
     * This function sets a random food item for a shop lineup by making a GET request to a specific
     * URL and updating the lineup with the retrieved data.
     *
     * @param i The parameter "i" is an integer that represents the index of the food item in the
     *          ShopLineup that needs to be set.
     */
    private void setShopFoods(int i) {
        showProgressDialog();
        String uri = Const.URL_ACCOUNTS + "/getRandomFood/" + getIntent().getExtras().get("id"); //id and picture

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null,
                response -> {
                    try {
                        ShopLineup.setFood(i, Integer.parseInt(response.get("id").toString()), response.get("picture").toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    hideProgressDialog();
                }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            System.out.println(error.getMessage());
            hideProgressDialog();
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

    /**
     * This function sets the brawlers in a shop lineup by making a GET request to a specified URL and
     * parsing the JSON response.
     *
     * @param i The parameter "i" is an integer that represents the index of the brawler in the shop
     *          lineup.
     */
    private void setShopBrawlers(int i) {
        showProgressDialog();
        String uri = Const.URL_ACCOUNTS + "/getShopBrawlerWithTurn/" + getIntent().getExtras().get("id");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null,
                response -> {
                    try {
                        Log.d("Test", response.toString());
                        int id = Integer.parseInt(response.get("id").toString());
                        int attack = Integer.parseInt(response.get("damage").toString());
                        int health = Integer.parseInt(response.get("health").toString());
                        int effectId = Integer.parseInt(response.get("a_id").toString());
                        BrawlerEffectType effectType = BrawlerEffectType.valueOf(response.get("a_time").toString());
                        String imageUrl = response.get("url").toString();
                        Log.d("Test", Integer.toString(id));
                        Log.d("Test", String.valueOf(effectType));
                        Log.d("Test", Integer.toString(effectId));
                        ShopLineup.setBrawler(i, id, attack, health, effectId, effectType, imageUrl);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    hideProgressDialog();
                }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            System.out.println(error.getMessage());
            hideProgressDialog();
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


    /**
     * This function sends a JSON array of brawler information to a server endpoint to update a user's
     * team lineup.
     */
    private void sendLineupBrawler() {
        showProgressDialog();
        String uri = Const.URL_ACCOUNTS + "/setTeam/" + getIntent().getExtras().get("id");

        JSONArray jsonArray = new JSONArray();
        int brawlerCount = 0;

        for (int i = 0; i < ActiveLineup.getLineup().length; i++) {
            if (ActiveLineup.getBrawler(i) != null) {
                Brawler brawler = ActiveLineup.getBrawler(i);
                Map<String, Integer> params = new HashMap<>();
                params.put("id", brawler.getId());
                params.put("damage", brawler.getAttack());
                params.put("health", brawler.getHealth());
                params.put("position", brawlerCount);
                brawlerCount++;
                jsonArray.put(new JSONObject(params));
            }
        }

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST,
                uri, jsonArray,
                response -> {
                    Log.d(TAG, response.toString());
                    hideProgressDialog();
                }, error -> {
            Log.d(TAG, jsonArray.toString());
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            System.out.println(error.getMessage());
            hideProgressDialog();
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
}
