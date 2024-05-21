package com.example.as1;

import static androidx.test.espresso.Espresso.onData;
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
public class LeagueTest {
    public ActivityTestRule<LeagueActivity> leagueActivityRule = new ActivityTestRule<>(LeagueActivity.class);

    @Before
    public void setup(){
        Intent i = new Intent();
        i.putExtra("username",  "jack");
        i.putExtra("password", "123");
        i.putExtra("accountType", 1);
        i.putExtra("signedIn", true);
        i.putExtra("id", 18);
        i.putExtra("league_id", 10);
        leagueActivityRule.launchActivity(i);
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
    }


    @Test
    public void addMember(){
        int resultLength = leagueActivityRule.getActivity().memberList.length + 1;

        leagueActivityRule.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        leagueActivityRule.getActivity().usernameInput.setText("neato28");
                    }
                });
        try {
            Thread.sleep(500); //wait for server
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.addMemberBtn)).perform(click());
        try {
            Thread.sleep(500); //wait for server
        } catch (InterruptedException e) {
        }
        assert(leagueActivityRule.getActivity().memberList.length == resultLength);
        //TODO add removeMember() function so leagues don't get flooded
    }

    @Test
    public void backButton(){
        String resultName = "jack";

        onView(withId(R.id.backBtn)).perform(click());
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){}
        onView(withId(R.id.usernameVal)).check(matches(withText(endsWith(resultName))));;
    }
}
