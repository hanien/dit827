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
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditingModeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void editingModeTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction cardView = onView(
                allOf(withId(R.id.CardViewFull),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                2),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txtTitle), withText("Reading mode"),
                        childAtPosition(
                                withParent(withId(R.id.modes_list)),
                                2),
                        isDisplayed()));
        textView.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.modeLabel), withText("Reading mode"), isDisplayed()));
        textView2.check(matches(withText("Reading mode")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.modeAirp), withText("22"), isDisplayed()));
        textView3.check(matches(withText("22")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.modeHum), withText("22"), isDisplayed()));
        textView4.check(matches(withText("22")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.modeLux), withText("2222"), isDisplayed()));
        textView5.check(matches(withText("2222")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.modeTemp), withText("22"), isDisplayed()));
        textView6.check(matches(withText("22")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.modeVolume), withText("2"), isDisplayed()));
        textView7.check(matches(withText("2")));

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.editBtn),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editName), withText("Reading mode"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("Writing mode"));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editName), withText("Writing mode"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editAirp), withText("22"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("1"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editAirp), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editHum), withText("22"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        appCompatEditText5.perform(scrollTo(), replaceText("2"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editHum), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.editLux), withText("2222"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1)));
        appCompatEditText7.perform(scrollTo(), replaceText("3"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.editLux), withText("3"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.editTemp), withText("22"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1)));
        appCompatEditText9.perform(scrollTo(), replaceText("4"));

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.editTemp), withText("4"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        appCompatEditText10.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.editVolume), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1)));
        appCompatEditText11.perform(scrollTo(), replaceText("5"));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.editVolume), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.saveBtn),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatImageView2.perform(scrollTo(), click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.txtTitle), withText("Writing mode"), isDisplayed()));
        textView8.check(matches(withText("Writing mode")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.txtTitle), withText("Writing mode"),
                        childAtPosition(
                                withParent(withId(R.id.modes_list)),
                                2),
                        isDisplayed()));
        textView9.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.modeAirp), withText("1"), isDisplayed()));
        textView10.check(matches(withText("1")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.modeHum), withText("2"), isDisplayed()));
        textView11.check(matches(withText("2")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.modeLux), withText("3"), isDisplayed()));
        textView12.check(matches(withText("3")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.modeTemp), withText("4"), isDisplayed()));
        textView13.check(matches(withText("4")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.modeVolume), withText("5"), isDisplayed()));
        textView14.check(matches(withText("5")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.modeLabel), withText("Writing mode"), isDisplayed()));
        textView15.check(matches(withText("Writing mode")));
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
