package com.example.as1;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignInTest {

    @Rule
    public ActivityScenarioRule<AccountActivity> accountActivityRule = new ActivityScenarioRule<>(AccountActivity.class);

    @Test
    public void signIntoAccount() {
        String username = "Jack";
        String password = "123";
        String resultName = "jack";

        accountActivityRule.getScenario().onActivity(activity -> {
            activity.usernameInput.setText(username);
            activity.passwordInput.setText(password);
        });
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){}
        onView(withId(R.id.signInBtn)).perform(click());
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        onView(withId(R.id.username)).check(matches(withText(endsWith(resultName))));
    }

/*    @Test
    public void profilePage(){
        String username = "Jack";
        String password = "123";
        String resultName = "jack";

        accountActivityRule.getScenario().onActivity(activity -> {
            activity.usernameInput.setText(username);
            activity.passwordInput.setText(password);
        });
        onView(withId(R.id.signInBtn)).perform(click());
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        onView(withId(R.id.signInBtn)).perform(click());
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        onView(withId(R.id.usernameVal)).check(matches(withText(endsWith(resultName))));
    }*/
}
