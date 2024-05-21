package com.example.as1;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Drew Kinneer
 * The `FoodFrame` class is a helper class for displaying food items in an Android app, allowing for
 * drag-and-drop functionality and displaying information about the food item in a dialog box.
 */
public class FoodFrame {
    private int index;
    private Activity activity;
    private Food food;
    private ImageView foodImage;
    private Dialog dialog;

    /**
     Represents the graphical frame of a food item displayed on the screen.
     The class adds functionality for handling user interactions, such as
     drag and drop actions and click events. It is responsible for displaying the food image and name,
     and showing a dialog with detailed information when clicked by the user.
     @param foodImage the ImageView representing the food image
     @param activity the Activity in which the FoodFrame is displayed
     @param index the position of the FoodFrame within the parent layout
     */
    FoodFrame(ImageView foodImage, Activity activity, int index){
        this.foodImage = foodImage;
        this.activity = activity;
        this.index = index;

        this.foodImage.setOnLongClickListener(v -> {
            ClipData dragData = ClipData.newPlainText("","");

            View.DragShadowBuilder myShadow = new MyDragShadowBuilder(v);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(dragData, myShadow, v, 0);
            }

            return true;
        });

        this.foodImage.setOnClickListener(v -> {
            if (this.food != null) {
                dialog = new Dialog(this.activity);
                dialog.setContentView(R.layout.brawler_dialog);

                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);

                ImageView dialogImageView = dialog.findViewById(R.id.brawlerImageView);
                dialogImageView.setImageDrawable(this.foodImage.getDrawable());

                TextView brawlerName = dialog.findViewById(R.id.brawlerName);
                brawlerName.setText(this.food.getName());

                TextView brawlerDesc = dialog.findViewById(R.id.brawlerDesc);
                brawlerDesc.setText(this.food.getEffectDesc());

                Button btnClose = dialog.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });
    }

    /**
     * The function returns a Food object.
     *
     * @return The method is returning an object of the class `Food`.
     */
    public Food getFood() {
        return food;
    }

    /**
     * The function returns an ImageView object representing a food image.
     *
     * @return An ImageView object named "foodImage" is being returned.
     */
    public ImageView getFoodImage() {
        return foodImage;
    }

    /**
     * This function sets the value of the "food" variable to the input "food" object.
     *
     * @param food The "food" parameter is an object of the class "Food". The method "setFood" sets the
     * value of the instance variable "food" to the value passed as the parameter.
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * The "clear" function sets the "food" variable to null and sets the image resource to 0.
     */
    public void clear() {
        this.food = null;
        this.foodImage.setImageResource(0);
    }

    /**
     * This function sets the image of a food item by decoding an image URL and displaying it in a
     * drawable format.
     */
    public void setFoodImageByUrl() {
        String imageUrl = this.food.getPhotoUrl();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
            Drawable d = new BitmapDrawable(Resources.getSystem(), bitmap);
            this.foodImage.setImageDrawable(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
