package com.example.as1;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.toolbox.ImageLoader;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import java.io.UnsupportedEncodingException;


import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * The BrawlerFrame class is a helper class for rendering and managing brawler images and their
 * associated stats in a mobile game app.
 * @author Drew Kinneer
 * @author Jack Kelley
 */
public class BrawlerFrame {
    protected int index;
    protected Activity activity;
    protected Brawler brawler;
    protected ImageView brawlerImage;
    protected TextView attack;
    protected TextView health;

    protected Dialog dialog;

    /**
     Creates a new instance of BrawlerFrame with the specified ImageView, Activity, and index.
     The ImageView represents the image of the brawler that will be displayed in the frame.
     The Activity is used to create the dialog box when the brawler is clicked.
     The index is used to determine the position of the brawler in the lineup.
     The constructor sets up a long click listener for the ImageView to enable drag and drop functionality.
     It also sets up a click listener for the ImageView to display a dialog box with information about the brawler.
     @param brawlerImage the ImageView representing the image of the brawler in the frame
     @param activity the Activity used to create the dialog box when the brawler is clicked
     @param index the index of the brawler in the lineup
     */
    BrawlerFrame(ImageView brawlerImage, Activity activity, int index) {
        this.brawlerImage = brawlerImage;
        this.activity = activity;
        this.index = index;

        this.brawlerImage.setOnLongClickListener(v -> {
            ClipData dragData = ClipData.newPlainText("", "");

            View.DragShadowBuilder myShadow = new MyDragShadowBuilder(v);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(dragData, myShadow, v, 0);
            }

            return true;
        });

        this.brawlerImage.setOnClickListener(v -> {
            if (brawler != null) {
                dialog = new Dialog(this.activity);
                dialog.setContentView(R.layout.brawler_dialog);

                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);

                ImageView dialogImageView = dialog.findViewById(R.id.brawlerImageView);
                dialogImageView.setImageDrawable(this.brawlerImage.getDrawable());

                TextView brawlerName = dialog.findViewById(R.id.brawlerName);
                brawlerName.setText(this.brawler.getName());

                TextView brawlerDesc = dialog.findViewById(R.id.brawlerDesc);
                brawlerDesc.setText(this.brawler.getBrawlerEffect().getEffectDesc());

                Button btnClose = dialog.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });
    }

    /**
     * This function renders the attack and health stats of a brawler in a ConstraintLayout.
     */
    protected void renderStats() {
        if (brawler != null) {
            ConstraintLayout layout = (ConstraintLayout) this.brawlerImage.getParent();

            layout.removeView(attack);
            layout.removeView(health);

            this.attack = new TextView(activity);
            this.health = new TextView(activity);

            if (brawler.getAttack() == 0 && brawler.getHealth() == 0){
                attack.setText("");
                health.setText("");
            }
            else{
                attack.setText(Integer.toString(brawler.getAttack()));
                health.setText(Integer.toString(brawler.getHealth()));
            }

            attack.setTextSize(30);
            health.setTextSize(30);

            ConstraintLayout.LayoutParams attackParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ConstraintLayout.LayoutParams healthParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            attackParams.topToTop = brawlerImage.getId();
            attackParams.rightToRight = brawlerImage.getId();

            healthParams.bottomToBottom = brawlerImage.getId();
            healthParams.rightToRight = brawlerImage.getId();

            layout.addView(attack, attackParams);
            layout.addView(health, healthParams);
            attack.setTextColor(Color.parseColor("#FFFFFF"));
            attack.setShadowLayer(10, 0, 0, Color.parseColor("#000000"));
            health.setTextColor(Color.parseColor("#FFFFFF"));
            health.setShadowLayer(10, 0, 0, Color.parseColor("#000000"));
        }
    }

    /**
     * The "clear" function sets the brawler to null, removes the brawler image, and removes the attack
     * and health views from the parent layout.
     */
    public void clear() {
        this.brawler = null;
        this.brawlerImage.setImageResource(R.drawable.empty);

        ConstraintLayout layout = (ConstraintLayout) this.brawlerImage.getParent();

        layout.removeView(attack);
        layout.removeView(health);
    }

    /**
     * The function returns a Brawler object.
     *
     * @return The method is returning an object of the class `Brawler`.
     */
    public Brawler getBrawler() {
        return brawler;
    }

    /**
     * The function sets the value of a Brawler object to a class variable.
     *
     * @param brawler The parameter "brawler" is an object of the class "Brawler". The method
     * "setBrawler" sets the value of the instance variable "brawler" to the value of the parameter
     * "brawler".
     */
    public void setBrawler(Brawler brawler) {
        this.brawler = brawler;
    }

    /**
     * The function returns an ImageView object representing a brawler image.
     *
     * @return The method is returning an ImageView object named "brawlerImage".
     */
    public ImageView getBrawlerImage() {
        return brawlerImage;
    }

    /**
     * This function sets the image of a brawler in a Java program.
     *
     * @param brawlerImage The parameter "brawlerImage" is an instance of the ImageView class, which is
     * used to display images in a graphical user interface (GUI) in Java. The method "setBrawlerImage"
     * sets the value of the instance variable "brawlerImage" to the value of the parameter passed to
     */
    public void setBrawlerImage(ImageView brawlerImage) {
        this.brawlerImage = brawlerImage;
    }

    /**
     * The function sets a brawler image by URL using an image loader and checks if the image is cached
     * before making a network call.
     *
     * @param imageUrl The URL of the image that needs to be loaded and displayed.
     */
    public void setBrawlerImageByUrl(String imageUrl) {

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // Loading image with placeholder and error image
        imageLoader.get(imageUrl, ImageLoader.getImageListener(
                this.brawlerImage, R.drawable.empty, R.drawable.empty));

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(imageUrl);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            // cached response doesn't exists. Make a network call here
        }
    }


    /**
     * The function returns the value of the variable "index".
     *
     * @return The index the brawlerFrame is in relative to its lineup
     */
    public int getIndex() {
        return index;
    }

    /**
     * This function sets the value of the "index" variable to the input parameter "index".
     *
     * @param index The index the brawlerFrame is in relative to its lineup
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
