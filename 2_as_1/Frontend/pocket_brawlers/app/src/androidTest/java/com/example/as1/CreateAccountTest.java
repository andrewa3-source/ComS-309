package com.example.as1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class CreateAccountTest {
    @Rule
    public ActivityScenarioRule<CreateAccountActivity> createAccountActivityRule = new ActivityScenarioRule<>(CreateAccountActivity.class);

    @Test
    public void createNewAccount() {
        String username = "neato28";
        String password = "123";
        String resultName = "neato28";

        createAccountActivityRule.getScenario().onActivity(activity -> {
            activity.usernameInput.setText(username);
            activity.passwordInput.setText(password);
            activity.confirmPasswordInput.setText(password);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        onView(withId(R.id.createAccountBtn)).perform(click());

        onView(withId(R.id.username)).check(matches(withText(endsWith(resultName))));
    }
}
