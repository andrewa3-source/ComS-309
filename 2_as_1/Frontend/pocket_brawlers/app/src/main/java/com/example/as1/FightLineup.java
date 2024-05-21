package com.example.as1;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;

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

import java.util.Random;

/**
 * @author Drew Kinneer
 * @author Jack Kelley
 * The FightLineup class manages a lineup of brawlers for a game, setting their stats and retrieving
 * data from a specified URL.
 */
public class FightLineup {
    protected FightBrawlerFrame[] lineup = new FightBrawlerFrame[5];

    /**
     * This function sets the lineup of brawlers for a game by making a GET request to a specified URL
     * and populating the lineup with the retrieved data, while also creating fake dead brawlers to
     * prevent null id checks.
     *
     * @param accountId The ID of the account for which the lineup is being set.
     * @param url The URL endpoint to retrieve the lineup data from.
     */
    protected void setLineup(int accountId, String url) {
        String uri = url + accountId;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null,
                response -> {
                    Log.d(FightLineup.class.getSimpleName(), response.toString());
                    try {
                        int brawlerCount = response.getInt("count");
                        for (int i = 0; i < brawlerCount; ++i) {
                            JSONObject brawlerObj = response.getJSONObject("b" + i);
                            //int index = brawlerObj.getInt("position");
                            int id = brawlerObj.getInt("id");
                            int attack = brawlerObj.getInt("damage");
                            int health = brawlerObj.getInt("health");
                            int effectId = brawlerObj.getInt("a_id");
                            BrawlerEffectType effectType = BrawlerEffectType.valueOf(brawlerObj.getString("a_time"));
                            String imageUrl = brawlerObj.getString("url");
                            setBrawler(i, id, attack, health, effectId, effectType, imageUrl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            /**
             * This function logs and prints an error message in case of a Volley error response.
             *
             * @param error The error parameter is an instance of the VolleyError class, which
             * represents an error that occurred during a Volley request. It contains information about
             * the type of error, such as a network error or a server error, as well as any error
             * message or additional data that may be available. The onErrorResponse method
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FightLineup.class.getSimpleName(), "AnError: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                "jobj_req");

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                //Create fake dead brawlers to prevent null id checks
                for (int i = 0; i < lineup.length; i++){
                    if (lineup[i].getBrawler() == null){
                        setBrawler(i, null);
                        lineup[i].setDead(true);
                    }
                }
            }

        }.start();
    }

    /**
     * This function sets a brawler in a specific index of a lineup and renders its stats.
     *
     * @param index The index represents the position of the brawler in the lineup. It is an integer
     * value that starts from 0 and goes up to the maximum number of brawlers in the lineup.
     * @param id The unique identifier of the brawler.
     * @param attack The attack parameter represents the amount of damage that the brawler can inflict
     * on its opponents. It is a measure of the brawler's offensive capabilities.
     * @param health The health parameter represents the amount of health points that a brawler has. It
     * determines how much damage the brawler can take before being defeated.
     * @param effectId The effectId parameter is an integer value that represents the unique identifier
     * of a specific effect that can be applied to a brawler. This effect can modify the brawler's
     * stats or behavior in some way, such as increasing its attack power or causing it to heal over
     * time. The effectId is used
     * @param brawlerEffect brawlerEffect is an enum type variable that represents the type of effect a
     * brawler has. It is used to set the effectId parameter of the Brawler object. The possible values
     * of BrawlerEffectType are defined elsewhere in the code and could include effects such as stun,
     * poison, or heal
     * @param imageUrl The URL of the image associated with the brawler.
     */
    protected void setBrawler(int index, int id, int attack, int health, int effectId, BrawlerEffectType brawlerEffect, String imageUrl) {
        lineup[index].clear();
        Brawler brawler = BrawlerIndex.getBrawler(id, attack, health, effectId, brawlerEffect, imageUrl);
        lineup[index].setBrawler(brawler);
        lineup[index].setBrawlerImageByUrl(imageUrl);
        lineup[index].setIndex(index);
        lineup[index].renderStats();
    }

    /**
     * This Java function sets a brawler at a specific index in a lineup, and if the brawler is null,
     * it sets a default brawler.
     *
     * @param index The index parameter is an integer value that represents the position of the brawler
     * in the lineup array. It is used to identify the specific slot where the brawler will be set.
     * @param brawler The brawler parameter is an object of the class Brawler, which contains
     * information about a specific character in a game or application. It likely includes attributes
     * such as name, health, attack power, and special abilities.
     */
    protected void setBrawler(int index, Brawler brawler){
        if (brawler == null){
            brawler = BrawlerIndex.getBrawler(0,0,0,0,null,"");
        }
        lineup[index].clear();
        lineup[index].setBrawler(brawler);
        lineup[index].setBrawlerImageByUrl(brawler.getImageUrl());
        lineup[index].setIndex(index);
        lineup[index].renderStats();
    }

    /**
     * The function generates a random integer between 1 and 4 (inclusive).
     *
     * @return An integer value between 1 and 4 (inclusive) generated randomly by the `nextInt()`
     * method of the `Random` class.
     */
    protected int randomId() {
        Random random = new Random();
        return random.nextInt(4) + 1;
    }

    /**
     * This function sets a Brawler object as the front brawler in a lineup and renders its stats.
     *
     * @param brawler The "brawler" parameter is an object of the class "Brawler" that is being passed
     * as an argument to the method "setFrontBrawler". This method is likely a part of a larger program
     * or system that involves managing a lineup of brawlers, and this particular method is responsible
     */
    protected void setFrontBrawler(Brawler brawler) {
        lineup[getFrontBrawlerFrame().getIndex()].setBrawler(brawler);
        lineup[getFrontBrawlerFrame().getIndex()].renderStats();
    }

    /**
     * This function sets the front brawler in a lineup as dead.
     */
    protected void setFrontBrawlerDead() {
        lineup[getFrontBrawlerFrame().getIndex()].setDead(true);
    }

    /**
     * The function checks if all the brawlers in the lineup are dead.
     *
     * @return The method `allDead()` returns a boolean value. It returns `true` if all the
     * `FightBrawlerFrame` objects in the `lineup` list have their `isDead` attribute set to `true`,
     * and `false` otherwise.
     */
    protected boolean allDead() {
        for (FightBrawlerFrame fightBrawlerFrame : lineup) {
            if (!fightBrawlerFrame.isDead) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function returns the first non-dead FightBrawlerFrame object in the lineup.
     *
     * @return The method is returning a non-dead `FightBrawlerFrame` object from the `lineup` list. If
     * there are no non-dead `FightBrawlerFrame` objects in the list, it returns `null`.
     */
    protected FightBrawlerFrame getFrontBrawlerFrame() {
        for (FightBrawlerFrame fightBrawlerFrame : lineup) {
            if (!fightBrawlerFrame.isDead) {
                return fightBrawlerFrame;
            }
        }
        return null;
    }

    /**
     * This Java function returns a string representation of a lineup object by iterating through its
     * brawlers and appending their string representations to the output.
     *
     * @return The method is returning a string representation of the lineup of brawlers. It
     * concatenates the string representation of each non-null brawler in the lineup and separates them
     * with a space.
     */
    @NonNull
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < lineup.length; i++) {
            if (lineup[i].getBrawler() != null) {
                output += lineup[i].getBrawler().toString();
                output += " ";
            }
        }
        return output;
    }
}
