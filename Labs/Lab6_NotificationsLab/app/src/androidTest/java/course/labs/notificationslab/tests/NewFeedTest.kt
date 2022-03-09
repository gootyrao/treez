package course.labs.notificationslab.tests

import android.test.ActivityInstrumentationTestCase2
import android.view.View
import com.robotium.solo.Solo
import course.labs.notificationslab.MainActivity
import course.labs.notificationslab.R
import course.labs.notificationslab.TestFrontEndActivity
import junit.framework.Assert

class NewFeedTest : ActivityInstrumentationTestCase2<TestFrontEndActivity>(TestFrontEndActivity::class.java) {
    private var solo: Solo? = null
    @Throws(Exception::class)
    override fun setUp() {
        solo = Solo(instrumentation, activity)
    }

    @Throws(Exception::class)
    override fun tearDown() {
        solo!!.finishOpenedActivities()
    }

    fun testRun() {
        val shortDelay = 5000
        val longDelay = 10000

        // Wait for activity:
        // 'course.labs.notificationslab.TestFrontEndActivity'
        solo!!.waitForActivity(
                TestFrontEndActivity::class.java,
                shortDelay)

        // Click on Make Tweets New
        solo!!.clickOnView(solo!!
                .getView(R.id.rejuv_tweets_button))

        // Click on Start Main Activity
        solo!!.clickOnView(solo!!
                .getView(R.id.start_main_button))

        // Wait for activity:
        // 'course.labs.threadslab.MainActivity'
        solo!!.waitForActivity(MainActivity::class.java,
                shortDelay)
        val msg = activity.getString(R.string.download_in_progress_string)
        assertFalse("'$msg	' is shown!",
                solo!!.searchText(msg))
        solo!!.waitForView(android.R.id.list)
        val listView = solo!!.getView(android.R.id.list)
        solo!!.waitForCondition({ listView.isEnabled }, longDelay)

        // Click on ladygaga
        solo!!.clickOnView(solo!!.getView(android.R.id.text1))

        // Assert that: 'feed_view' is shown
        assertTrue("feed_view not shown!", solo!!.waitForView<View?>(solo!!
                .getView(R.id.feed_view)))

        // Assert that: 'Taylor Swift' is shown
        Assert.assertTrue("'Lady Gaga' is not shown!",
                solo!!.searchText("Lady Gaga"))
    }
}