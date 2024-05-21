package com.example.as1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

import android.content.Intent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProfileTest {

    public ActivityTestRule<SignedInActivity> signedInActivityRule = new ActivityTestRule<>(SignedInActivity.class);

    @Before
    public void setup(){
        Intent i = new Intent();
        i.putExtra("username",  "jack");
        i.putExtra("password", "123");
        i.putExtra("accountType", 1);
        i.putExtra("signedIn", true);
        i.putExtra("id", 18);
        i.putExtra("league_id", 10);
        signedInActivityRule.launchActivity(i);
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void leaguePage(){
        String resultName = "League 10";

        onView(withId(R.id.leagueBtn)).perform(click());

        onView(withId(R.id.leagueId)).check(matches(withText(endsWith(resultName))));
    }

    @Test
    public void changePasswordPage(){
        String resultName = "jack";

        onView(withId(R.id.changePasswordBtn)).perform(click());
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){}
        onView(withId(R.id.backBtn)).perform(click());
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){}
        onView(withId(R.id.usernameVal)).check(matches(withText(endsWith(resultName))));
    }

    @Test
    public void backButton(){
        String resultName = "jack";

        onView(withId(R.id.backBtn)).perform(click());

        onView(withId(R.id.username)).check(matches(withText(endsWith(resultName))));
    }

    @Test
    public void signoutButton(){
        String resultName = "Not signed in";

        onView(withId(R.id.signoutBtn)).perform(click());

        onView(withId(R.id.username)).check(matches(withText(endsWith(resultName))));
    }
}
