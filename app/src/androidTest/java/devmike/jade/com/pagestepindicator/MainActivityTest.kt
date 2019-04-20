package devmike.jade.com.pagestepindicator

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.GeneralLocation
import android.support.test.espresso.action.GeneralSwipeAction
import android.support.test.espresso.action.Press
import android.support.test.espresso.action.Swipe
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.ViewPagerActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Hide the PageStepIndicator to allow this test to run
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {


    @get:Rule
    val mainActivityTest = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSwipe(){
        onView(withId(R.id.vp)).check(matches(isDisplayed())).perform(ViewPagerActions.scrollRight()).perform(ViewPagerActions.scrollLeft())
    }


}