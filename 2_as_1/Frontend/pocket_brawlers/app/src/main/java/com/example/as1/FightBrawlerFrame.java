package com.example.as1;

import android.app.Activity;
import android.widget.ImageView;

/**
 * The FightBrawlerFrame class extends the BrawlerFrame class and adds a boolean variable to indicate
 * if the brawler is dead or not.
 * @author Drew Kinneer
 */
public class FightBrawlerFrame extends BrawlerFrame {
    boolean isDead = true;

    /**
     A subclass of BrawlerFrame used specifically for displaying brawlers during fights.
     @param brawlerImage the ImageView representing the brawler's image
     @param activity the activity hosting the brawler frame
     @param index the index of the brawler in the lineup
     */
    FightBrawlerFrame(ImageView brawlerImage, Activity activity, int index) {
        super(brawlerImage, activity, index);
    }

    /**
     * The function sets the value of a boolean variable "isDead" to the value of the parameter "dead".
     *
     * @param dead If "dead" is true, it means that the brawler is dead, and if it is
     * false, it means that the brawler is alive.
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * This function sets a Brawler and sets the isDead variable to false.
     *
     * @param brawler The parameter "brawler" is an object of the class "Brawler". It is being passed
     * as an argument to the method "setBrawler".
     */
    @Override
    public void setBrawler(Brawler brawler) {
        super.setBrawler(brawler);
        this.isDead = false;
    }
}
