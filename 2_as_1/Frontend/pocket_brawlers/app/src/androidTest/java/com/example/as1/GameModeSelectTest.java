package com.example.as1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GameModeSelectTest {

    public ActivityTestRule<GameModeSelectActivity> gameModeSelectActivityRule = new ActivityTestRule<>(GameModeSelectActivity.class);

    @Before
    public void setup(){
        Intent i = new Intent();
        i.putExtra("username",  "jack");
        i.putExtra("password", "123");
        i.putExtra("accountType", 1);
        i.putExtra("signedIn", true);
        i.putExtra("id", 18);
        i.putExtra("league_id", 10);
        gameModeSelectActivityRule.launchActivity(i);
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void singleplayerButton(){
        String resultName = "Select Game Mode";

        onView(withId(R.id.singleplayerBtn)).perform(click());
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){}
        onView(withId(R.id.backBtn)).perform(click());

        onView(withId(R.id.selectGameTitle)).check(matches(withText(endsWith(resultName))));
    }

    @Test
    public void multiplayerButton(){
        String resultName = "Select Game Mode";

        onView(withId(R.id.multiplayerBtn)).perform(click());
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){}
        onView(withId(R.id.backBtn)).perform(click());

        onView(withId(R.id.selectGameTitle)).check(matches(withText(endsWith(resultName))));
    }
}