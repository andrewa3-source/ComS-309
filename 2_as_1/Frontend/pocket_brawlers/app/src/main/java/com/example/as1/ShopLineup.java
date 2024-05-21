package com.example.as1;

import android.app.Activity;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Drew Kinneer
 * @author Jack Kelley
 * The `ShopLineup` class manages the lineup of brawlers and food items in a shop, allowing for
 * setting, getting, and removing of these objects.
 */
public class ShopLineup {
    private static ShopBrawlerFrame[] brawlerLineup = new ShopBrawlerFrame[3];
    private static FoodFrame[] foodLineup = new FoodFrame[2];

    /**
     This class represents a lineup of shop items, which includes brawlers and food items.
     It initializes the lineup by creating instances of ShopBrawlerFrame and FoodFrame for each
     brawler and food item in the lineup, respectively.
     @param activity the activity to which this lineup belongs
     */
    ShopLineup(Activity activity) {
        ImageView shop1 = activity.findViewById(R.id.brawler1);
        ImageView shop2 = activity.findViewById(R.id.brawler2);
        ImageView shop3 = activity.findViewById(R.id.brawler3);

        ImageView food1 = activity.findViewById(R.id.food1);
        ImageView food2 = activity.findViewById(R.id.food2);

        brawlerLineup[0] = new ShopBrawlerFrame(null, shop1, activity, 0);
        brawlerLineup[1] = new ShopBrawlerFrame(null, shop2, activity, 1);
        brawlerLineup[2] = new ShopBrawlerFrame(null, shop3, activity, 2);

        foodLineup[0] = new FoodFrame(food1, activity, 0);
        foodLineup[1] = new FoodFrame(food2, activity, 1);
    }

    /**
     * The function returns a random integer between 1 and 4 inclusive.
     *
     * @return The method `randomIndex()` returns a random integer between 1 and 4 (inclusive).
     */
    private int randomIndex() {
        Random random = new Random();
        return random.nextInt(4) + 1;
    }

    /**
     * The function sets a brawler in a specific spot in the lineup with given attributes and renders
     * its stats.
     *
     * @param spot The index of the spot in the brawler lineup where the new brawler will be set.
     * @param id The unique identifier for the brawler.
     * @param attack The attack value of the brawler being set. It represents how much damage the
     * brawler can deal to opponents.
     * @param health The health parameter represents the amount of health points that a brawler has. It
     * determines how much damage the brawler can take before being defeated.
     * @param effectId The effectId parameter is an integer that represents the unique identifier of
     * the brawler's effect. This is used to associate a specific effect with a brawler and can be used
     * to trigger the effect during gameplay.
     * @param effectType effectType is an enum type variable of the BrawlerEffectType class. It
     * represents the type of effect that a brawler has, such as a stun effect or a poison effect.
     * @param imageUrl A string representing the URL of the image for the brawler. This is used to
     * display the brawler's image in the user interface.
     */
    public static void setBrawler(int spot, int id, int attack, int health, int effectId, BrawlerEffectType effectType, String imageUrl){
        brawlerLineup[spot].clear();
        Brawler brawler = BrawlerIndex.getBrawler(id, attack, health, effectId, effectType, imageUrl);
        brawlerLineup[spot].setBrawler(brawler);
        brawlerLineup[spot].setBrawlerImageByUrl(brawler.getImageUrl());
        brawlerLineup[spot].setIndex(spot);
        brawlerLineup[spot].renderStats();
    }

    /**
     * The function returns a Brawler object from an array of BrawlerLineup objects at a specified
     * index.
     *
     * @param index The parameter "index" is an integer value that represents the position of the
     * brawler in the "brawlerLineup" array that we want to retrieve. It is used as an index to access
     * the specific element in the array.
     * @return The method `getBrawler()` is being called on an object in the `brawlerLineup` array at
     * the specified `index`, and the `Brawler` object returned by that method is being returned by the
     * `getBrawler(int index)` method.
     */
    public static Brawler getBrawler(int index){
        return brawlerLineup[index].getBrawler();
    }

    /**
     * The function removes a brawler from a lineup at a specified index.
     *
     * @param index The parameter "index" is an integer value that represents the index of the brawler
     * to be removed from the "brawlerLineup" array. The method "removeBrawler" takes this index as
     * input and clears the element at that index in the array.
     */
    public static void removeBrawler(int index){
        brawlerLineup[index].clear();
    }

    /**
     * The function returns a specific food item from an array of food objects based on the index
     * provided.
     *
     * @param index The parameter "index" is an integer value that represents the index position of an
     * element in an array. In this case, it is used to retrieve a specific food item from an array of
     * foodLineup.
     * @return The method `getFood` is returning an object of type `Food`. The specific `Food` object
     * being returned depends on the value of the `index` parameter passed to the method.
     */
    public static Food getFood(int index){
        return foodLineup[index].getFood();
    }

    /**
     * This function sets the food item at a specific spot in the food lineup with a given ID and image
     * URL.
     *
     * @param spot The index of the spot in the food lineup array where the food item will be set.
     * @param id The id parameter is an integer that represents the unique identifier of a food item.
     * It is used to retrieve the corresponding food object from the FoodIndex.
     * @param url The "url" parameter is a String variable that represents the URL (Uniform Resource
     * Locator) of an image that will be used to display the food item.
     */
    public static void setFood(int spot, int id, String url){
        foodLineup[spot].clear();
        foodLineup[spot].setFood(FoodIndex.getFood(id, url));
        foodLineup[spot].setFoodImageByUrl();
    }

    /**
     * This function removes the food item at the specified index from the food lineup.
     *
     * @param index The parameter "index" is an integer value that represents the index of the element
     * in the "foodLineup" array that needs to be removed. The method "removeFood" takes this index as
     * input and clears the element at that index.
     */
    public static void removeFood(int index){
        foodLineup[index].clear();
    }

}
