package com.example.as1;

import android.view.animation.Animation;

import androidx.annotation.NonNull;

/**
 * The FightAnimation class represents a specific animation for a brawler in a fighting game, including
 * the type of animation, the brawler frame, and the new health value.
 * @author Drew Kinneer
 */
public class FightAnimation {
    private FightBrawlerFrame brawlerFrame;
    private int animId; // 1 = left attacks, 2 = right attacks, 3 = death, 4 = player win, 5 = player lose, 6 = tie
    private Animation animation;
    private int newHealth;

    /**
     Constructs a new FightAnimation object for animating a brawler's health bar and playing an animation.
     @param brawlerFrame the FightBrawlerFrame to animate
     @param animId the ID of the animation to play
     @param newHealth the new health value to set the brawler to after the animation completes
     @param animation the Animation object to play
     */
    FightAnimation(FightBrawlerFrame brawlerFrame, int animId, int newHealth, Animation animation){
        this.brawlerFrame = brawlerFrame;
        this.animId = animId;
        this.newHealth = newHealth;
        this.animation = animation;
    }

    /**
     * The function returns a FightBrawlerFrame object.
     *
     * @return The method is returning an object of type `FightBrawlerFrame`.
     */
    public FightBrawlerFrame getBrawlerFrame() {
        return brawlerFrame;
    }

    /**
     * The function returns the value of the variable "animId".
     *
     * @return The method is returning an integer value, specifically the value of the variable
     * `animId`.
     */
    public int getAnimId() {
        return animId;
    }

    /**
     * The function returns an animation object.
     *
     * @return The method is returning an object of type `Animation`.
     */
    public Animation getAnimation() {return animation;}

    @NonNull
    @Override
    public String toString() {
        return brawlerFrame.getBrawler().toString() + " Animation ID:" + animId;
    }
}
