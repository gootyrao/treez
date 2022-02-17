package course.labs.permissionslab.tests


import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import course.labs.permissionslab.ActivityLoaderActivity
import course.labs.permissionslab.R
import junit.framework.Assert.assertTrue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestDangerousActivityPermission {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(ActivityLoaderActivity::class.java)

//    @Rule
//    @JvmField
//    var mGrantPermissionRule =
//        GrantPermissionRule.grant(
//            "course.labs.permissions.DANGEROUS_ACTIVITY_PERM"
//        )

    @Test
    fun dangerousActivityPermission() {
        val button = onView(
            allOf(
                withId(R.id.start_phone_status_button), withText("PhoneStatus Activity"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        button.perform(click())

        val button2 = onView(
            allOf(
                withId(R.id.go_to_dangerous_activity_button), withText("Go To DangerousActivity"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        button2.perform(click())

        val button3 = onView(
            allOf(
                withId(R.id.start_dangerous_activity_button), withText("Start Dangerous Activity"),
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
        button3.perform(click())
        grantPermission()
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

    fun grantPermission() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        if (Build.VERSION.SDK_INT >= 23) {
            val allowPermission = UiDevice.getInstance(instrumentation).findObject(
                UiSelector().text("Allow")
            )
            assertTrue(allowPermission.exists())
            allowPermission.click()

        }
    }
}
