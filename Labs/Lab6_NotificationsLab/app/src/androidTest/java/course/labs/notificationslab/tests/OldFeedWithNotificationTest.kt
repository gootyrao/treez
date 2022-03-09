package course.labs.notificationslab.tests

import android.test.ActivityInstrumentationTestCase2
import com.robotium.solo.Solo
import course.labs.notificationslab.TestFrontEndActivity

class OldFeedWithNotificationTest : ActivityInstrumentationTestCase2<TestFrontEndActivity>(TestFrontEndActivity::class.java) {
    private var solo: Solo? = null
    @Throws(Exception::class)
    override fun setUp() {
        solo = Solo(instrumentation)
        activity
    }

    @Throws(Exception::class)
    override fun tearDown() {
        solo!!.finishOpenedActivities()
    }

}