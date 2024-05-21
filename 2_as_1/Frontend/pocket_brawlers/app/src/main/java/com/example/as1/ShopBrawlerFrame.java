package com.example.as1;

import android.app.Activity;
import android.widget.ImageView;

/**
 * @author Drew Kinneer
 * The `ShopBrawlerFrame` class is a constructor that takes in a `Brawler` object, an `ImageView`
 * object, an `Activity` object, and an integer index, and renders the brawler's stats.
 */
public class ShopBrawlerFrame extends BrawlerFrame {
    /**
     * A class representing the frame of a brawler in the shop, which extends the BrawlerFrame class.
     * It is used to display the brawler image, name, and price in the shop.
     * @param brawler      the brawler object to display in the frame
     * @param brawlerImage the ImageView to display the brawler image
     * @param activity     the activity in which the frame is displayed
     * @param index        the index of the brawler in the shop
     */
    ShopBrawlerFrame(Brawler brawler, ImageView brawlerImage, Activity activity, int index) {
        super(brawlerImage, activity, index);
        this.brawler = brawler;
        if (this.brawler != null) {
            setBrawlerImageByUrl(this.brawler.getImageUrl());
        }

        renderStats();
    }
}
