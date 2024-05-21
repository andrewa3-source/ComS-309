package com.example.as1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Test;

public class ChatTest {

    public ActivityTestRule<ChatActivity> chatActivityRule = new ActivityTestRule<>(ChatActivity.class);

    @Before
    public void setup(){
        Intent i = new Intent();
        i.putExtra("username",  "jack");
        i.putExtra("password", "123");
        i.putExtra("accountType", 1);
        i.putExtra("signedIn", true);
        i.putExtra("id", 18);
        i.putExtra("league_id", 10);
        chatActivityRule.launchActivity(i);
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void sendChat(){
        String s = chatActivityRule.getActivity().chat.getText().toString();
        String result = s + "\njack: hello";

        chatActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatActivityRule.getActivity().textEnter.setText("hello");
            }
        });

        onView(withId(R.id.sendBtn)).perform(click());

        onView(withId(R.id.chat)).check(matches(withText(endsWith(result))));
    }
}
