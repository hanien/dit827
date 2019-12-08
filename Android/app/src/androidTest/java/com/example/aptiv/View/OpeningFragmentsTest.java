package com.example.aptiv.View;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.aptiv.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OpeningFragmentsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openingFragmentTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction cardView = onView(
                allOf(withId(R.id.CardViewLux),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.tempLabel), withText("Current lux"), isDisplayed()));
        textView.check(matches(withText("Current lux")));

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.cancelButton),
                        childAtPosition(
                                allOf(withId(R.id.Temperature),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.CardViewFull),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                2),
                        isDisplayed()));
        cardView2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.txtLabel), withText("Settings"), isDisplayed()));
        textView2.check(matches(withText("Settings")));

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.cancel),
                        childAtPosition(
                                allOf(withId(R.id.Settings),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.LinearLayoutTemp),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tempLabel), withText("Current Temperature"), isDisplayed()));
        textView3.check(matches(withText("Current Temperature")));

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.cancelButton),
                        childAtPosition(
                                allOf(withId(R.id.Temperature),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.LinearLayoutTemp),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.tempLabel), withText("Current Temperature"), isDisplayed()));
        textView4.check(matches(withText("Current Temperature")));

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.cancelButton),
                        childAtPosition(
                                allOf(withId(R.id.Temperature),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.LinearLayoutVolume),
                        childAtPosition(
                                allOf(withId(R.id.CardViewVolume),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        linearLayout3.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.tempLabel), withText("Current Sound Level"), isDisplayed()));
        textView5.check(matches(withText("Current Sound Level")));

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.cancelButton),
                        childAtPosition(
                                allOf(withId(R.id.Temperature),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction cardView3 = onView(
                allOf(withId(R.id.CardViewPressure),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                1),
                        isDisplayed()));
        cardView3.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.tempLabel), withText("Current air pressure"), isDisplayed()));
        textView6.check(matches(withText("Current air pressure")));

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(R.id.cancelButton),
                        childAtPosition(
                                allOf(withId(R.id.Temperature),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView6.perform(click());

        ViewInteraction cardView4 = onView(
                allOf(withId(R.id.CardViewHumidity),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                2),
                        isDisplayed()));
        cardView4.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.tempLabel), withText("Current humidity"), isDisplayed()));
        textView7.check(matches(withText("Current humidity")));

        ViewInteraction appCompatImageView7 = onView(
                allOf(withId(R.id.cancelButton),
                        childAtPosition(
                                allOf(withId(R.id.Temperature),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView7.perform(click());

        ViewInteraction appCompatImageView8 = onView(
                allOf(withId(R.id.HandlerBtn),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                8),
                        isDisplayed()));
        appCompatImageView8.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.Txt1), withText("Control Nearby Devices"), isDisplayed()));
        textView8.check(matches(withText("Control Nearby Devices")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.X), withText("X"),
                        childAtPosition(
                                allOf(withId(R.id.DH),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

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
