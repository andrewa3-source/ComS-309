package com.example.as1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @author Drew Kinneer
 * The `MyDragShadowBuilder` class creates a draggable image that fills the canvas provided by the
 * system and draws a `ColorDrawable` on a canvas passed in from the system.
 */
public class MyDragShadowBuilder extends View.DragShadowBuilder {

    // The drag shadow image, defined as a drawable object.
    private static Drawable shadow;

    // This is a constructor for the `MyDragShadowBuilder` class that takes a `View` object as a
    // parameter. It calls the constructor of the parent class `View.DragShadowBuilder` with the same
    // `View` object as a parameter using the `super` keyword. It also creates a new `ColorDrawable`
    // object with a light gray color and assigns it to the `shadow` variable. This `ColorDrawable`
    // object will be used as the drag shadow image.
    public MyDragShadowBuilder(View v) {

        // Stores the View parameter.
        super(v);

        // Creates a draggable image that fills the Canvas provided by the system.
        shadow = new ColorDrawable(Color.LTGRAY);
    }

    /**
     * This function sets the size and position of a drag shadow based on the size of the original
     * view.
     *
     * @param size A Point object that represents the size of the drag shadow. The method sets the
     * width and height of the drag shadow using this parameter. The size of the drag shadow is half
     * the size of the original View.
     * @param touch The touch parameter represents the position of the user's touch on the screen,
     * relative to the top-left corner of the drag shadow. In this method, it is being set to the
     * middle of the drag shadow by dividing the width and height of the shadow by 2. This is done so
     * that the
     */
    @Override
    public void onProvideShadowMetrics(Point size, Point touch) {

        // Defines local variables
        int width, height;

        // Set the width of the shadow to half the width of the original View.
        width = getView().getWidth() / 2;

        // Set the height of the shadow to half the height of the original View.
        height = getView().getHeight() / 2;

        // The drag shadow is a ColorDrawable. This sets its dimensions to be the
        // same as the Canvas that the system provides. As a result, the drag shadow
        // fills the Canvas.
        shadow.setBounds(0, 0, width, height);

        // Set the size parameter's width and height values. These get back to the
        // system through the size parameter.
        size.set(width, height);

        // Set the touch point's position to be in the middle of the drag shadow.
        touch.set(width / 2, height / 2);
    }

    /**
     * This function draws a ColorDrawable on a Canvas passed in from the system.
     *
     * @param canvas Canvas is a class in Android that provides a 2D drawing API for rendering graphics
     * on a surface. It is used to draw shapes, text, images, and other graphical elements on the
     * screen. In this case, the onDrawShadow method is called when a View is being dragged and a
     * shadow
     */
    @Override
    public void onDrawShadow(Canvas canvas) {

        // Draw the ColorDrawable on the Canvas passed in from the system.
        shadow.draw(canvas);
    }
}