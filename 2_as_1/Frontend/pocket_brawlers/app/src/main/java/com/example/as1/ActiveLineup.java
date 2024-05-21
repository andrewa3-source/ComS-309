package com.example.as1;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The ActiveLineup class manages the active brawler lineup in a game and allows for swapping and
 * setting brawlers.
 * @author Drew Kinneer
 */
public class ActiveLineup {
    static ActiveBrawlerFrame[] lineup = new ActiveBrawlerFrame[5];
    private static boolean firstTurn = true;

    /**
     Creates a new instance of the ActiveLineup class with the specified activity.
     @param activity the activity in which the active lineup is used.
     */
    ActiveLineup(Activity activity) {
        ImageView spot1 = activity.findViewById(R.id.spot1);
        ImageView spot2 = activity.findViewById(R.id.spot2);
        ImageView spot3 = activity.findViewById(R.id.spot3);
        ImageView spot4 = activity.findViewById(R.id.spot4);
        ImageView spot5 = activity.findViewById(R.id.spot5);

        lineup[0] = new ActiveBrawlerFrame(spot1, activity, 0);
        lineup[1] = new ActiveBrawlerFrame(spot2, activity, 1);
        lineup[2] = new ActiveBrawlerFrame(spot3, activity, 2);
        lineup[3] = new ActiveBrawlerFrame(spot4, activity, 3);
        lineup[4] = new ActiveBrawlerFrame(spot5, activity, 4);

        if (!ActiveLineup.firstTurn) {
            setLineup(activity.getIntent().getExtras().getInt("id"));
        }
    }

    /**
     * This function sets the lineup of brawlers for a given account by making a GET request to a
     * specified URL and parsing the JSON response.
     *
     * @param accountId The ID of the account for which the lineup is being set.
     */
    protected void setLineup(int accountId) {
        String uri = Const.URL_ACCOUNTS + "/getFormattedTeam/" + accountId;
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                uri, null,
                response -> {
                    Log.d(FightLineup.class.getSimpleName(), response.toString());
                    try {
                        int brawlerCount = response.length();
                        for (int i = 0; i < brawlerCount; ++i) {
                            JSONObject brawlerObj = response.getJSONObject(i);

                            int index = brawlerObj.getInt("position");
                            int id = brawlerObj.getInt("id");
                            int attack = brawlerObj.getInt("damage");
                            int health = brawlerObj.getInt("health");
                            int effectId = brawlerObj.getInt("a_id");
                            BrawlerEffectType effectType = BrawlerEffectType.valueOf(brawlerObj.getString("a_time"));
                            String imageUrl = brawlerObj.getString("url");
                            lineup[index].clear();
                            Brawler brawler = BrawlerIndex.getBrawler(id, attack, health, effectId, effectType, imageUrl);
                            lineup[index].setBrawler(brawler);
                            lineup[index].setBrawlerImageByUrl(imageUrl);
                            lineup[index].setIndex(index);
                            lineup[index].renderStats();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FightLineup.class.getSimpleName(), "Error: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                "jobj_req");
    }

    /**
     * The function swaps the positions of two brawlers in a lineup.
     *
     * @param newIndex The index of the lineup where the brawler will be moved to.
     * @param currentIndex The index of the brawler in the current lineup that needs to be swapped with
     * another brawler.
     */
    public static void swapLineupBrawlers(int newIndex, int currentIndex) {
        if (newIndex == currentIndex) {
            return;
        }

        Brawler tempBrawler = lineup[newIndex].getBrawler();
        Brawler currentBrawler = lineup[currentIndex].getBrawler();

        ActiveLineup.removeBrawler(currentIndex);
        ActiveLineup.removeBrawler(newIndex);


        if (currentBrawler != null) {
            lineup[newIndex].setBrawler(currentBrawler);
            lineup[newIndex].setBrawlerImageByUrl(currentBrawler.getImageUrl());
            lineup[newIndex].renderStats();
        }
        lineup[currentIndex].setBrawlerImageByUrl(tempBrawler.getImageUrl());
        lineup[currentIndex].setBrawler(tempBrawler);
        lineup[currentIndex].renderStats();
    }

    /**
     * This function sets the value of a boolean variable called "firstTurn" in the ActiveLineup class.
     *
     * @param firstTurn a boolean variable that represents whether it is the first turn of the game or
     * not. If it is the first turn, certain actions or rules may need to be adjusted accordingly.
     */
    public static void setFirstTurn(boolean firstTurn) {
        ActiveLineup.firstTurn = firstTurn;
    }

    /**
     * This function checks if a lineup of brawlers is empty by iterating through each brawler and
     * checking if it is null.
     *
     * @return The method `isLineupEmpty()` returns a boolean value. It returns `true` if the lineup is
     * empty (i.e., all elements in the lineup array have a null brawler), and `false` otherwise.
     */
    public static boolean isLineupEmpty(){
        for (int i = 0; i < getLineup().length; i++){
            if (lineup[i].getBrawler() != null){
                return false;
            }
        }

        return true;
    }

    public static int numBrawlersInLineup(){
        int num = 0;
        for (int i = 0; i < getLineup().length; i++){
            if (lineup[i].getBrawler() != null){
                num++;
            }
        }
        return num;
    }

    /**
     * The function returns an array of ActiveBrawlerFrame objects representing a lineup.
     *
     * @return The method is returning an array of ActiveBrawlerFrame objects named "lineup".
     */
    public static ActiveBrawlerFrame[] getLineup() {
        return lineup;
    }

    /**
     * This function sets a Brawler object at a specific spot in an array and renders its stats.
     *
     * @param spot The spot parameter is an integer that represents the index of the Brawler in the
     * lineup array that we want to set the new Brawler to.
     * @param brawler The "brawler" parameter is an object of the class "Brawler". It is being passed
     * as an argument to the method "setBrawler" along with an integer value "spot". The method sets
     * the brawler object at the specified spot in the "lineup" array and then calls
     */
    public static void setBrawler(int spot, Brawler brawler) {
        lineup[spot].setBrawler(brawler);
        lineup[spot].renderStats();
    }

    /**
     * The function returns the Brawler object at a specified spot in the lineup array.
     *
     * @param spot The parameter "spot" is an integer that represents the position of a brawler in a
     * lineup. The method "getBrawler" returns the brawler object at the specified position in the
     * lineup.
     * @return The method `getBrawler` is returning an object of type `Brawler`. The specific `Brawler`
     * object being returned depends on the value of the parameter `spot`, which is used to access an
     * element of the `lineup` array and call the `getBrawler` method on it.
     */
    public static Brawler getBrawler(int spot) {
        return lineup[spot].getBrawler();
    }

    /**
     * The function removes a brawler from a lineup at a specified index.
     *
     * @param index The parameter "index" is an integer value that represents the position of the
     * brawler to be removed from the "lineup" array. The method "removeBrawler" takes this index as
     * input and clears the element at that index in the "lineup" array.
     */
    public static void removeBrawler(int index) {
        lineup[index].clear();
    }

    @NonNull
    public String toString() {
        String output = "";
        for (int i = 0; i < lineup.length; i++){
            Brawler brawler = lineup[i].getBrawler();
            if (brawler != null) {
                output += brawler;
            } else {
                output += "null";
            }
        }
        return output;
    }
}
