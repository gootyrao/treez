package course.labs.intentslab.tests


import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import course.labs.intentslab.ActivityLoaderActivity
import course.labs.intentslab.R
import org.junit.Assert.assertTrue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class ImplicitIntentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(ActivityLoaderActivity::class.java)
    private var uiDevice: UiDevice? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }


    @Test
    fun implicitIntentTest() {
        val button = onView(
            allOf(
                withId(R.id.implicit_activation_button), withText("Implicit Activation"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button.perform(click())

        val testButton = uiDevice!!.findObject(UiSelector().text("MyDialer"))
        assertTrue(testButton.exists())
        if (testButton.exists() && testButton.isEnabled) {
            Log.i("Hello", "Check")
            testButton.click()
        }
        uiDevice!!.pressHome()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
