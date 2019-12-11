package com.example.aptiv.View;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.aptiv.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainUIExistenceTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainUITest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageView = onView(
                allOf(withId(R.id.HandlerBtn), isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.default_car), isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.lightImage), isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.altitudeimg), isDisplayed()));
        imageView4.check(matches(isDisplayed()));

        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.settingsImageView), isDisplayed()));
        imageView5.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("IN\nTEMP"), isDisplayed()));
        textView.check(matches(withText("IN\nTEMP")));

        ViewInteraction textView2 = onView(
                allOf(withText("OUT\nTEMP"), isDisplayed()));
        textView2.check(matches(withText("OUT\nTEMP")));

        ViewInteraction imageView6 = onView(
                allOf(withId(R.id.soundImageView), isDisplayed()));
        imageView6.check(matches(isDisplayed()));

        ViewInteraction imageView7 = onView(
                allOf(withId(R.id.airPressure), isDisplayed()));
        imageView7.check(matches(isDisplayed()));

        ViewInteraction imageView8 = onView(
                allOf(withId(R.id.humidity), isDisplayed()));
        imageView8.check(matches(isDisplayed()));

    }
}
