package com.example.roombooker;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HelpPageFirstSwitchTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void helpPageActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.helpButton), withText("Help"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.bobCakeTextView), withText("Bob does NOT like cake"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bodyHolder),
                                        1),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Bob does NOT like cake")));

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.bobCake), withText("Does Bob like cake?"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bodyHolder),
                                        1),
                                0)));
        switch_.perform(scrollTo(), click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.bobCakeTextView), withText("Bob likes cake"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bodyHolder),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Bob likes cake")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
