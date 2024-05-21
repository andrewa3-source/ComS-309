package com.example.as1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;
import android.view.InputDevice;
import android.view.MotionEvent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ShopTests {

    public ActivityTestRule<BrawlerActivity> activityRule = new ActivityTestRule<>(BrawlerActivity.class);;

    @Before
    public void setup(){
        Intent i = new Intent();
        i.putExtra("username",  "jack");
        i.putExtra("password", "123");
        i.putExtra("accountType", 1);
        i.putExtra("signedIn", true);
        i.putExtra("id", 18);
        i.putExtra("league_id", 10);
        i.putExtra("useWebsockets", false);
        activityRule.launchActivity(i);
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.as1", appContext.getPackageName());
    }

    @Test
    public void rollButton() {
        String result = "9";
        onView(withId(R.id.rollBtn)).perform(click());
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){}

        onView(withId(R.id.doubloonsText)).check(matches(withText(endsWith(result))));
    }

    @Test
    public void dragBrawler() {
        String result = "Shop";
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActiveLineup.removeBrawler(0);
                Brawler testBrawler = BrawlerIndex.getBrawler(3, 2, 2, 2, BrawlerEffectType.valueOf("BUY"), "https://i.imgur.com/4gkdoAO.png");
                ActiveLineup.setBrawler(0, testBrawler);
            }
        });
        onView(withId(R.id.battleBtn)).perform(click());
        try{
            Thread.sleep(10000);
        } catch (InterruptedException e){}

        onView(withId(R.id.textView)).check(matches(withText(endsWith(result))));
    }
}