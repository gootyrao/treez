package course.labs.permissionslab.tests


import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import course.labs.permissionslab.ActivityLoaderActivity
import course.labs.permissionslab.R
import junit.framework.Assert
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.StringContains.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PhoneNumberPermissionTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(ActivityLoaderActivity::class.java)

//    @Rule
//    @JvmField
//    var mGrantPermissionRule =
//        GrantPermissionRule.grant(
//            "android.permission.SEND_SMS",
//            "android.permission.READ_PHONE_NUMBERS",
//            "android.permission.READ_PHONE_STATE"
//        )

    @Test
    fun phoneNumberPermissionTest() {
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
                withId(R.id.get_phone_number_button), withText("Get Phone Number"),
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
        button2.perform(click())
        grantPermission()
        val textView = onView(
            allOf(
                withId(R.id.text),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView.check(matches(withText(containsString("Phone Number: +"))))
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
            Assert.assertTrue(allowPermission.exists())
            allowPermission.click()
        }
    }
}
