package com.example.as1;

import android.app.Activity;
import android.widget.TextView;

/**
 * @author Drew Kinneer
 * The PlayerStats class represents the player's statistics, including health and doubloon values, and
 * provides methods to update and retrieve these values.
 */
public class PlayerStats {

    private static int healthVal;
    private static int doubloonsVal;

    private static TextView doubloonsText;

    private Activity activity;

    /**
     A class representing the player's statistics.
     This class initializes the player's starting health and doubloon values, and also provides a reference to the
     activity in which these values are displayed.
     @param activity the activity in which the player's doubloon value is displayed
     */
    PlayerStats(Activity activity){
        healthVal = 5;
        doubloonsVal = 10;

        this.activity = activity;
        doubloonsText = this.activity.findViewById(R.id.doubloonsText);
        doubloonsText.setText(Integer.toString(doubloonsVal));
    }

    /**
     * This function subtracts 3 from a variable called doubloonsVal, sets it to a minimum of 0, and
     * updates a text field with the new value.
     */
    public static void buy(){
        doubloonsVal -= 3;
        doubloonsVal = Math.max(0, doubloonsVal);
        doubloonsText.setText(Integer.toString(doubloonsVal));
    }

    /**
     * The function increases the value of doubloonsVal by 1 and updates the doubloonsText display.
     */
    public static void sell(){
        doubloonsVal += 1;
        doubloonsVal = Math.max(0, doubloonsVal);
        doubloonsText.setText(Integer.toString(doubloonsVal));
    }

    /**
     * This function decreases the value of a variable called doubloonsVal by 1 and updates a text
     * field with the new value.
     */
    public static void roll(){
        doubloonsVal -= 1;
        doubloonsVal = Math.max(0, doubloonsVal);
        doubloonsText.setText(Integer.toString(doubloonsVal));
    }

    /**
     * The function returns the value of a variable called "healthVal".
     *
     * @return The method is returning an integer value, which is the value of the variable
     * `healthVal`.
     */
    public static int getHealthVal() {
        return healthVal;
    }

    /**
     * The function returns the value of a variable called "doubloonsVal".
     *
`     * @return The method is returning the value of the static variable `doubloonsVal`.
     */
    public static int getDoubloonsVal() {
        return doubloonsVal;
    }
}
