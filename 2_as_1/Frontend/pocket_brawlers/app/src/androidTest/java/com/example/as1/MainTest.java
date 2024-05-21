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
public class MainTest {

    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        Intent i = new Intent();
        i.putExtra("username",  "jack");
        i.putExtra("password", "123");
        i.putExtra("accountType", 1);
        i.putExtra("signedIn", true);
        i.putExtra("id", 18);
        i.putExtra("league_id", 10);
        mainActivityRule.launchActivity(i);
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void startButton(){
        String resultName = "jack";

        onView(withId(R.id.toBrawlersBtn)).perform(click());
        onView(withId(R.id.backBtn)).perform(click());

        onView(withId(R.id.username)).check(matches(withText(endsWith(resultName))));
    }

    @Test
    public void chatButton(){
        String resultName = "jack";

        onView(withId(R.id.chatBtn)).perform(click());
        onView(withId(R.id.backBtn)).perform(click());

        onView(withId(R.id.username)).check(matches(withText(endsWith(resultName))));
    }
}
