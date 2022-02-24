package course.labs.fragmentslab


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PhoneTestAll {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun phoneTestAll() {
        val textView = onView(
            allOf(
                withId(android.R.id.text1), withText("ladygaga"),
                withParent(
                    allOf(
                        withId(android.R.id.list),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("ladygaga")))

        val textView2 = onView(
            allOf(
                withId(android.R.id.text1), withText("msrebeccablack"),
                withParent(
                    allOf(
                        withId(android.R.id.list),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("msrebeccablack")))

        val textView3 = onView(
            allOf(
                withId(android.R.id.text1), withText("taylorswift13"),
                withParent(
                    allOf(
                        withId(android.R.id.list),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("taylorswift13")))


        val textView5 = onData(anything())
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
        textView5.perform(click())

        val textView6 = onView(
            allOf(
                withId(R.id.feed_view), withText(
                    //"Lady Gaga - Go to http://t.co/W2NqZiTtOo to see @Terry_World Terry Richardson's photos of me getting ready for the show!\n\nLady Gaga - I love u! RT @BoyGeorge: Comeback? Babe I had no idea u went anywhere The 1st part was very Liza I loved it! Such rich tones More of that! x\n\nLady Gaga - @TrollMarkus I felt so alive! All I remember is the audience cheering! I could barely hear! Hands in the air, smiles &amp; flashes everywhere!\n\nLady Gaga - @gabicorradin30 breathing\n\nLady Gaga - That is correct. RT @dope_cinema: only @LadyGaga would purposely put booing in her performance hahah\n\nLady Gaga - @RobbyRizl it was a canvas, and I AM the canvas during this performance\n\nLady Gaga - RT @leonardo_bv: @ladygaga The choreography was perfect, mimetizing each era, and the booing during Born This Way era was such a great stat�\n\nLady Gaga - @LiamCalderone I wrote it special for this performance!\n\nLady Gaga - RT @Born2BeBrave: @ladygaga competition has no place in music, if people want competition they can watch football. Music is about art and e�\n\nLady Gaga - @BadKid_Earthfan aisle 5\n\nLady Gaga - @vuittonbrunette everyone always asks why? Why did she do this? Why did she do that? My answer: For the Applause!\n\nLady Gaga - @WonderBoyxGAGA for the fame monster! yellow wig from the telephone video and monsterball!\n\nLady Gaga - So what did monsters think of my Vma performance? Stay focused on your greatest competition: yourself! #SnatchYourOwnWeave\n\n"
                    "Lady Gaga - Go to http://t.co/W2NqZiTtOo to see @Terry_World Terry Richardson's photos of me getting ready for the show!\n\nLady Gaga - I love u! RT @BoyGeorge: Comeback? Babe I had no idea u went anywhere The 1st part was very Liza I loved it! Such rich tones More of that! x\n\nLady Gaga - @TrollMarkus I felt so alive! All I remember is the audience cheering! I could barely hear! Hands in the air, smiles &amp; flashes everywhere!\n\nLady Gaga - @gabicorradin30 breathing\n\nLady Gaga - That is correct. RT @dope_cinema: only @LadyGaga would purposely put booing in her performance hahah\n\nLady Gaga - @RobbyRizl it was a canvas, and I AM the canvas during this performance\n\nLady Gaga - RT @leonardo_bv: @ladygaga The choreography was perfect, mimetizing each era, and the booing during Born This Way era was such a great stat…\n\nLady Gaga - @LiamCalderone I wrote it special for this performance!\n\nLady Gaga - RT @Born2BeBrave: @ladygaga competition has no place in music, if people want competition they can watch football. Music is about art and e…\n\nLady Gaga - @BadKid_Earthfan aisle 5\n\nLady Gaga - @vuittonbrunette everyone always asks why? Why did she do this? Why did she do that? My answer: For the Applause!\n\nLady Gaga - @WonderBoyxGAGA for the fame monster! yellow wig from the telephone video and monsterball!\n\nLady Gaga - So what did monsters think of my Vma performance? Stay focused on your greatest competition: yourself! #SnatchYourOwnWeave\n\n"

                ),
                withParent(withParent(withId(R.id.fragment_container))),
                isDisplayed()
            )
        )
        textView6.check(
            matches(
                withText(
                    containsString("Lady Gaga - Go to http://t.co/W2NqZiTtOo to see @Terry_World Terry Richardson's photos of me getting ready for the show!") // \n\nLady Gaga - I love u! RT @BoyGeorge: Comeback? Babe I had no idea u went anywhere The 1st part was very Liza I loved it! Such rich tones More of that! x\n\nLady Gaga - @TrollMarkus I felt so alive! All I remember is the audience cheering! I could barely hear! Hands in the air, smiles &amp; flashes everywhere!\n\nLady Gaga - @gabicorradin30 breathing\n\nLady Gaga - That is correct. RT @dope_cinema: only @LadyGaga would purposely put booing in her performance hahah\n\nLady Gaga - @RobbyRizl it was a canvas, and I AM the canvas during this performance\n\nLady Gaga - RT @leonardo_bv: @ladygaga The choreography was perfect, mimetizing each era, and the booing during Born This Way era was such a great stat�\n\nLady Gaga - @LiamCalderone I wrote it special for this performance!\n\nLady Gaga - RT @Born2BeBrave: @ladygaga competition has no place in music, if people want competition they can watch football. Music is about art and e�\n\nLady Gaga - @BadKid_Earthfan aisle 5\n\nLady Gaga - @vuittonbrunette everyone always asks why? Why did she do this? Why did she do that? My answer: For the Applause!\n\nLady Gaga - @WonderBoyxGAGA for the fame monster! yellow wig from the telephone video and monsterball!\n\nLady Gaga - So what did monsters think of my Vma performance? Stay focused on your greatest competition: yourself! #SnatchYourOwnWeave\n\n"
                )
            )
        )

        pressBack()

        val textView7 = onData(anything())
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
        textView7.perform(click())

        val textView8 = onView(
            allOf(
                withId(R.id.feed_view), withText(
                    "Rebecca Black - @JuanDirection_4\n\nRebecca Black - so someone wrote this on my desk http://t.co/nR7TMzhe31\n\nRebecca Black - @maddislifee DONT TELL ME WHAT TO DO MADDI\n\nRebecca Black - I liked a @YouTube video http://t.co/ij6BlYiu9a CAMP TAKOTA DAY 11\n\nRebecca Black - so tired someone save me from school\n\nRebecca Black - @idkgrapes yep lol\n\nRebecca Black - @TaliaCBrown yep\n\nRebecca Black - shout out to my sass at the 2011 vmas #throwback http://t.co/fgk6TWyJ2j\n\nRebecca Black - @Maryssahhh I've known the kid for two years now. he's worked his butt off to get where he is now and he definitely does.\n\nRebecca Black - @MrBlahargith it's my brothers birthday!\n\nRebecca Black - just heard @AustinMahone won a moon man tonight! its CRAAAZY to see how far you've come over the past couple of years. you deserve it!! :')\n\nRebecca Black - @sarahmweyand @taylorswift13 WAIT WHAT\n\nRebecca Black - in case you haven't seen...im in @RickyPDillon's video this week! GO WATCH NOW AND MAYBE YOU'LL EVEN GET A FOLLOW! ;) http://t.co/ScdijvXAXQ\n\nRebecca Black - selfieeeeee with the birthday boy http://t.co/oWfdWegwNr\n\n"
                ),
                withParent(withParent(withId(R.id.fragment_container))),
                isDisplayed()
            )
        )
        textView8.check(
            matches(
                withText(
                    containsString("Rebecca Black - so someone wrote this on my desk http://t.co/nR7TMzhe31")
                )
            )
        )

        pressBack()

        val textView9 = onData(anything())
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
        textView9.perform(click())

        val textView10 = onView(
            allOf(
                withId(R.id.feed_view), withText(
                    containsString("Headed to the VMAs")
                ),
                withParent(withParent(withId(R.id.fragment_container))),
                isDisplayed()
            )
        )
        textView10.check(
            matches(
                withText(
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
