package course.labs.fragmentslab


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TabletTestAll {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun tabletTest() {
        val textView = onData(anything())
            .inAdapterView(
                allOf(
                    withId(android.R.id.list),
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        1
                    )
                )
            )
            .atPosition(0)
        textView.check(ViewAssertions.matches(ViewMatchers.withText("ladygaga")))
        textView.perform(click())

        val textView6 = Espresso.onView(
            allOf(
                withId(R.id.feed_view), ViewMatchers.withText(
                    //"Lady Gaga - Go to http://t.co/W2NqZiTtOo to see @Terry_World Terry Richardson's photos of me getting ready for the show!\n\nLady Gaga - I love u! RT @BoyGeorge: Comeback? Babe I had no idea u went anywhere The 1st part was very Liza I loved it! Such rich tones More of that! x\n\nLady Gaga - @TrollMarkus I felt so alive! All I remember is the audience cheering! I could barely hear! Hands in the air, smiles &amp; flashes everywhere!\n\nLady Gaga - @gabicorradin30 breathing\n\nLady Gaga - That is correct. RT @dope_cinema: only @LadyGaga would purposely put booing in her performance hahah\n\nLady Gaga - @RobbyRizl it was a canvas, and I AM the canvas during this performance\n\nLady Gaga - RT @leonardo_bv: @ladygaga The choreography was perfect, mimetizing each era, and the booing during Born This Way era was such a great stat�\n\nLady Gaga - @LiamCalderone I wrote it special for this performance!\n\nLady Gaga - RT @Born2BeBrave: @ladygaga competition has no place in music, if people want competition they can watch football. Music is about art and e�\n\nLady Gaga - @BadKid_Earthfan aisle 5\n\nLady Gaga - @vuittonbrunette everyone always asks why? Why did she do this? Why did she do that? My answer: For the Applause!\n\nLady Gaga - @WonderBoyxGAGA for the fame monster! yellow wig from the telephone video and monsterball!\n\nLady Gaga - So what did monsters think of my Vma performance? Stay focused on your greatest competition: yourself! #SnatchYourOwnWeave\n\n"
                    containsString("Lady Gaga - I love u! RT @BoyGeorge: Comeback?")
                ),
//                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.fragment_container))),
                ViewMatchers.isDisplayed()
            )
        )
        textView6.check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    containsString("Lady Gaga - Go to http://t.co/W2NqZiTtOo to see @Terry_World Terry Richardson's photos of me getting ready for the show!") // \n\nLady Gaga - I love u! RT @BoyGeorge: Comeback? Babe I had no idea u went anywhere The 1st part was very Liza I loved it! Such rich tones More of that! x\n\nLady Gaga - @TrollMarkus I felt so alive! All I remember is the audience cheering! I could barely hear! Hands in the air, smiles &amp; flashes everywhere!\n\nLady Gaga - @gabicorradin30 breathing\n\nLady Gaga - That is correct. RT @dope_cinema: only @LadyGaga would purposely put booing in her performance hahah\n\nLady Gaga - @RobbyRizl it was a canvas, and I AM the canvas during this performance\n\nLady Gaga - RT @leonardo_bv: @ladygaga The choreography was perfect, mimetizing each era, and the booing during Born This Way era was such a great stat�\n\nLady Gaga - @LiamCalderone I wrote it special for this performance!\n\nLady Gaga - RT @Born2BeBrave: @ladygaga competition has no place in music, if people want competition they can watch football. Music is about art and e�\n\nLady Gaga - @BadKid_Earthfan aisle 5\n\nLady Gaga - @vuittonbrunette everyone always asks why? Why did she do this? Why did she do that? My answer: For the Applause!\n\nLady Gaga - @WonderBoyxGAGA for the fame monster! yellow wig from the telephone video and monsterball!\n\nLady Gaga - So what did monsters think of my Vma performance? Stay focused on your greatest competition: yourself! #SnatchYourOwnWeave\n\n"
                )
            )
        )

        val textView2 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(android.R.id.list),
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        1
                    )
                )
            )
            .atPosition(1)
        textView2.perform(click())
        textView2.check(ViewAssertions.matches(ViewMatchers.withText("msrebeccablack")))
        val textView7 = Espresso.onView(
            allOf(
                withId(R.id.feed_view), ViewMatchers.withText(
                    //"Lady Gaga - Go to http://t.co/W2NqZiTtOo to see @Terry_World Terry Richardson's photos of me getting ready for the show!\n\nLady Gaga - I love u! RT @BoyGeorge: Comeback? Babe I had no idea u went anywhere The 1st part was very Liza I loved it! Such rich tones More of that! x\n\nLady Gaga - @TrollMarkus I felt so alive! All I remember is the audience cheering! I could barely hear! Hands in the air, smiles &amp; flashes everywhere!\n\nLady Gaga - @gabicorradin30 breathing\n\nLady Gaga - That is correct. RT @dope_cinema: only @LadyGaga would purposely put booing in her performance hahah\n\nLady Gaga - @RobbyRizl it was a canvas, and I AM the canvas during this performance\n\nLady Gaga - RT @leonardo_bv: @ladygaga The choreography was perfect, mimetizing each era, and the booing during Born This Way era was such a great stat�\n\nLady Gaga - @LiamCalderone I wrote it special for this performance!\n\nLady Gaga - RT @Born2BeBrave: @ladygaga competition has no place in music, if people want competition they can watch football. Music is about art and e�\n\nLady Gaga - @BadKid_Earthfan aisle 5\n\nLady Gaga - @vuittonbrunette everyone always asks why? Why did she do this? Why did she do that? My answer: For the Applause!\n\nLady Gaga - @WonderBoyxGAGA for the fame monster! yellow wig from the telephone video and monsterball!\n\nLady Gaga - So what did monsters think of my Vma performance? Stay focused on your greatest competition: yourself! #SnatchYourOwnWeave\n\n"
                    containsString("Rebecca Black - so someone wrote this on my desk")
                ),
//                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.fragment_container))),
                ViewMatchers.isDisplayed()
            )
        )
        textView7.check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    containsString("DONT TELL ME WHAT TO DO MADDI") // \n\nLady Gaga - I love u! RT @BoyGeorge: Comeback? Babe I had no idea u went anywhere The 1st part was very Liza I loved it! Such rich tones More of that! x\n\nLady Gaga - @TrollMarkus I felt so alive! All I remember is the audience cheering! I could barely hear! Hands in the air, smiles &amp; flashes everywhere!\n\nLady Gaga - @gabicorradin30 breathing\n\nLady Gaga - That is correct. RT @dope_cinema: only @LadyGaga would purposely put booing in her performance hahah\n\nLady Gaga - @RobbyRizl it was a canvas, and I AM the canvas during this performance\n\nLady Gaga - RT @leonardo_bv: @ladygaga The choreography was perfect, mimetizing each era, and the booing during Born This Way era was such a great stat�\n\nLady Gaga - @LiamCalderone I wrote it special for this performance!\n\nLady Gaga - RT @Born2BeBrave: @ladygaga competition has no place in music, if people want competition they can watch football. Music is about art and e�\n\nLady Gaga - @BadKid_Earthfan aisle 5\n\nLady Gaga - @vuittonbrunette everyone always asks why? Why did she do this? Why did she do that? My answer: For the Applause!\n\nLady Gaga - @WonderBoyxGAGA for the fame monster! yellow wig from the telephone video and monsterball!\n\nLady Gaga - So what did monsters think of my Vma performance? Stay focused on your greatest competition: yourself! #SnatchYourOwnWeave\n\n"
                )
            )
        )


        val textView3 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(android.R.id.list),
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        1
                    )
                )
            )
            .atPosition(2)
        textView3.check(ViewAssertions.matches(ViewMatchers.withText("taylorswift13")))
        textView3.perform(click())
        val textView10 = Espresso.onView(
            allOf(
                withId(R.id.feed_view), ViewMatchers.withText(
                    containsString("Headed to the VMAs")
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView10.check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    containsString("Taylor Swift - I love you guys so much.") //\n\nTaylor Swift - Headed to the VMAs. So. Excited.\n\nTaylor Swift - RT @markvillaver: Taylor Swift &amp; Jennifer Lopez - Jenny from the Block - RED Tour - L.A. Staples Center Sat 8/24/2013 http://t.co/WUtebAqJk�\n\nTaylor Swift - RT @JLo: @taylorswift13  had so much fun with you tonight!!! #RedTourLA  #jennyfromtheblock #hairbrushsongs\n\nTaylor Swift - Sang Jenny From the Block with @JLo tonight at Staples Center. STILL FANGIRLING OUT ABOUT IT.\n\nTaylor Swift - RT @JLo: #Red!!! @taylorswift13 @ STAPLES Center http://t.co/iVbun7jXtg\n\nTaylor Swift - Our last show in LA is tonight. Can't wait to see what's in store......\n\nTaylor Swift - RT @siananderson: List of things I was put on this earth to do - this http://t.co/7x4NjvmyhG\n\nTaylor Swift - Now I've seen it through, and now I know the truth... That anything could happen. http://t.co/B1V8G55MJM\n\nTaylor Swift - RT @elliegoulding: Still blown away by how incredible the @taylorswift13 show is and how lucky I feel to have been a part of that last night\n\nTaylor Swift - RT @elliegoulding: So.much.fun. Love that girl http://t.co/AzmX6SsQeb\n\nTaylor Swift - So.. Anything could happen at one of our LA shows. @elliegoulding showed up to sing 'anything could happen'! 15,000 person dance party.\n\nTaylor Swift - RT @MTVNews: #IKnewYouWereTrouble co-star @reevecarney takes us through @taylorswift13's #VMA-nom vid frame-by-frame! http://t.co/3JhEJt34tH\n\nTaylor Swift - RT @teganandsara: Remember when we played #closer with @taylorswift13 yesterday? Ya. Me too. Living the dream. Seriously. Feel so lucky. Th�\n\n"
                )
            )
        )
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
