package com.example.as1;

import android.app.Activity;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * The ActiveBrawlerFrame class extends the BrawlerFrame class and handles drag and drop events for
 * purchasing brawlers, moving brawlers in the lineup, and giving food to brawlers.
 * @author Drew Kinneer
 */
public class ActiveBrawlerFrame extends BrawlerFrame {
    /**
     Creates a new instance of the ActiveBrawlerFrame class with the specified brawler image, activity, and index.
     @param brawlerImage the ImageView representing the brawler.
     @param activity the activity in which the brawler frame is used.
     @param index the index of the brawler frame in the active lineup.
     */
    ActiveBrawlerFrame(ImageView brawlerImage, Activity activity, int index) {
        super(brawlerImage, activity, index);
        this.brawlerImage = brawlerImage;
        this.activity = activity;
        this.index = index;

        this.brawlerImage.setOnDragListener((v, e) -> {

            View view = (View) e.getLocalState();
            int dragEvent = e.getAction();

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true; //Return true to indicate this View can accept drag
                case DragEvent.ACTION_DROP:
                    int shopIndex = -1;
                    int spotIndex = -1;
                    int foodIndex = -1;

                    //Purchasing a brawler
                    if (PlayerStats.getDoubloonsVal() >= 3) {
                        if (view.getId() == R.id.brawler1) {
                            shopIndex = 0;
                        } else if (view.getId() == R.id.brawler2) {
                            shopIndex = 1;
                        } else if (view.getId() == R.id.brawler3) {
                            shopIndex = 2;
                        }
                    }

                    //Moving a brawler in the lineup
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

                    //Giving food to a brawler
                    if (PlayerStats.getDoubloonsVal() >= 3) {
                        if (view.getId() == R.id.food1) {
                            foodIndex = 0;
                        } else if (view.getId() == R.id.food2) {
                            foodIndex = 1;
                        }
                    }

                    if (shopIndex != -1 && this.brawler == null) { //Buying brawler
                        PlayerStats.buy();
                        this.brawler = ShopLineup.getBrawler(shopIndex);
                        if (this.brawler.getBrawlerEffect().getEffectType() == BrawlerEffectType.BUY){ //Run buy effect
                            this.brawler.getBrawlerEffect().doEffect();
                        }
                        this.setBrawlerImageByUrl(ShopLineup.getBrawler(shopIndex).getImageUrl());
                        ShopLineup.removeBrawler(shopIndex);
                        renderStats();
                        return true;
                    } else if (spotIndex != -1 && ActiveLineup.getLineup()[spotIndex].getBrawler() != null) { //swapping brawlers
                        ActiveLineup.swapLineupBrawlers(spotIndex, this.index);
                        return true;
                    } else if (foodIndex != -1 && this.brawler != null){ //Buying food
                        PlayerStats.buy();
                        this.brawler = ShopLineup.getFood(foodIndex).doEffect(this.brawler);
                        ShopLineup.removeFood(foodIndex);
                        renderStats();
                        return true;
                    }

            }
            return false;
        });
    }
}
