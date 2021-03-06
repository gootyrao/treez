package course.labs.notificationslab

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : FragmentActivity(), FriendsFragment.SelectionListener,
    DownloadFinishedListener {

    private lateinit var mFriendsFragment: FriendsFragment
    private lateinit var mFeedFragment: FeedFragment
    private lateinit var mDownloaderFragment: DownloaderTaskFragment

    private var mIsInteractionEnabled = false
    private var mFormattedFeeds: Array<String?>? = arrayOfNulls<String?>(sRawTextFeedIds.size)
    private var mIsFresh = false
    private var mRefreshReceiver: BroadcastReceiver? = null
    var mResultCode = RESULT_CANCELED

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Starting MainActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFeedFragment = FeedFragment()

        // Reset instance state on reconfiguration
        if (null != savedInstanceState) {
            restoreState(savedInstanceState)
        } else {
            setupFragments()
        }
    }

  // One time setup of UI and retained (headless) Fragment
    private fun setupFragments() {
        Log.i(TAG, "Entering setupFragments")

        installFriendsFragment()

        // The feed is fresh if it was downloaded less than 2 minutes ago
        mIsFresh = System.currentTimeMillis() - getFileStreamPath(
                TWEET_FILENAME).lastModified() < TWO_MIN
        Log.i(TAG, mIsFresh.toString())
        if (!mIsFresh) { // If feed needs updating
            installDownloaderTaskFragment()

            // TODO: (complete, not tested) Show a Toast message displaying download in progress
            Toast.makeText(applicationContext,
                getString(R.string.download_in_progress_string), Toast.LENGTH_LONG).show()

            // Set up a BroadcastReceiver to receive an Intent when download
            // finishes.
            mRefreshReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    // TODO: (Done, need testing) Check to make sure this is an ordered broadcast
                    // Let sender know that the Intent was received
                    // by setting result code to MainActivity.IS_ALIVE
                    if (isOrderedBroadcast) {
                        Log.i(TAG, "Setting result to IS_ALIVE")
                        mResultCode = IS_ALIVE
//                        setResult(mResultCode)
                        intent!!.putExtra("resultCode", IS_ALIVE)
                    }
                }
            }
        } else { // If feed does not need updating
            // Process Twitter data taken from stored file
            parseJSON(loadTweetsFromFile())

            // Enable user interaction
            mIsInteractionEnabled = true
        }

        Log.i(TAG, "Exiting setupFragments")
    }

    // Add Friends Fragment to Activity
    private fun installFriendsFragment() {

        // Make new Fragment
        mFriendsFragment = FriendsFragment()

        // Give Fragment to the FragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, mFriendsFragment,
                TAG_FRIENDS_FRAGMENT)
        transaction.commit()
    }

    // Add DownloaderTaskFragment to Activity
    private fun installDownloaderTaskFragment() {

        // Make new Fragment
        mDownloaderFragment = DownloaderTaskFragment()

        // Set DownloaderTaskFragment arguments
        val args = Bundle()
        args.putIntegerArrayList(TAG_FRIEND_RES_IDS, sRawTextFeedIds)
        mDownloaderFragment.arguments = args

        // Give Fragment to the FragmentManager
        supportFragmentManager.beginTransaction()
                .add(mDownloaderFragment, TAG_DOWNLOADER_FRAGMENT).commit()
    }

    // Register the BroadcastReceiver
    override fun onResume() {
        super.onResume()

        // TODO: (Done, need testing) Register the BroadcastReceiver to receive a DATA_REFRESHED_ACTION broadcast
        registerReceiver(mRefreshReceiver, IntentFilter(DATA_REFRESHED_ACTION))
    }

    override fun onPause() {

        // TODO: (Done, need testing) Unregister the BroadcastReceiver if it has been registered
        // Note: check that mRefreshReceiver is not null before attempting to
        // unregister in order to work around an Instrumentation issue
        if (mRefreshReceiver != null) {
            unregisterReceiver(mRefreshReceiver)
        }

        super.onPause()
    }
    
    /*
	 * DownloadFinishedListener method
	 */
    // Called back by DownloaderTask after data has been loaded
    override fun notifyDataRefreshed(feeds: Array<String?>?) {

        // Process downloaded data
        parseJSON(feeds)

        // Enable user interaction
        mIsInteractionEnabled = true
    }

    // Installs the FeedFragment when a Friend name is
    // selected in the FriendsFragment
    override fun onItemSelected(position: Int) {

        if (mIsInteractionEnabled) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mFeedFragment)
                .addToBackStack(null)
                .commit()

            supportFragmentManager.executePendingTransactions()

            mFeedFragment.updateFeedDisplay(position)
        }
    }

    // Restore saved instance state
    private fun restoreState(savedInstanceState: Bundle?) {

        // Fragments tags were saved in onSavedInstanceState
        mFriendsFragment = supportFragmentManager
            .findFragmentByTag(
                savedInstanceState!!
                    .getString(TAG_FRIENDS_FRAGMENT)
            ) as FriendsFragment
        mFeedFragment = supportFragmentManager
            .findFragmentByTag(
                savedInstanceState
                    .getString(TAG_FEED_FRAGMENT)
            ) as FeedFragment
        mDownloaderFragment = supportFragmentManager
            .findFragmentByTag(
                savedInstanceState
                    .getString(TAG_DOWNLOADER_FRAGMENT)
            ) as DownloaderTaskFragment
        mIsInteractionEnabled = savedInstanceState
            .getBoolean(TAG_IS_DATA_AVAILABLE)
        if (mIsInteractionEnabled) {
            mFormattedFeeds = savedInstanceState
                .getStringArray(TAG_PROCESSED_FEEDS)
        }
    }

    // Convert raw data (in JSON format) into text for display
    private fun parseJSON(feeds: Array<String?>?) {
        val jsonFeeds = arrayOfNulls<JSONArray?>(feeds!!.size)
        for (i in jsonFeeds.indices) {
            try {
                jsonFeeds[i] = JSONArray(feeds[i])
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            var name = ""
            var tweet = ""
            val tmp = jsonFeeds[i]

            // string buffer for feeds
            val tweetRec = StringBuffer("")
            for (j in 0 until tmp!!.length()) {
                try {
                    tweet = tmp.getJSONObject(j).getString(TAG_TEXT)
                    val user = tmp.getJSONObject(j)[TAG_USER] as JSONObject
                    name = user.getString(TAG_NAME)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                tweetRec.append("$name - $tweet\n\n")
            }
//            mFormattedFeeds.get(i) = tweetRec.toString()
        }
    }

    // Retrieve feeds text from a file
    // Store them in mRawTextFeed[]
    private fun loadTweetsFromFile(): Array<String?> {
        var reader: BufferedReader? = null
        val rawFeeds = ArrayList<String?>()
        try {
            val fis = openFileInput(TWEET_FILENAME)
            reader = BufferedReader(InputStreamReader(fis))
            var s: String?
            var i = 0
            while (null != reader.readLine().also { s = it }) {
                rawFeeds.add(i, s)
                i++
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return rawFeeds.toTypedArray()
    }

    companion object {
        private const val TAG_NAME: String = "name"
        private const val TAG_USER: String = "user"
        private const val TAG_TEXT: String = "text"
        private const val TAG_FRIENDS_FRAGMENT: String = "friends_fragment"
        private const val TAG_FEED_FRAGMENT: String = "feed_fragment"
        private const val TAG_DOWNLOADER_FRAGMENT: String = "downloader_fragment"
        private const val TAG_IS_DATA_AVAILABLE: String = "is_data_available"
        private const val TAG_PROCESSED_FEEDS: String = "processed_feeds"

        const val TAG_FRIEND_RES_IDS: String = "friends"
        const val TWEET_FILENAME: String = "tweets.txt"
        const val IS_ALIVE = RESULT_FIRST_USER
        const val DATA_REFRESHED_ACTION: String = "course.labs.notificationslabnew.DATA_REFRESHED"

        private const val TAG: String = "Lab-Notifications"

        // Raw feed file IDs used to reference stored tweet data
        val sRawTextFeedIds: ArrayList<Int> =
            arrayListOf(R.raw.ladygaga, R.raw.rebeccablack, R.raw.taylorswift)
        private const val TWO_MIN = 2 * 60 * 1000.toLong()
    }

    /*
        Behavior seen:
            ran app w/ starting point as MainActivity


            Test 1.1
                - toast message displayed in app
            ~6 seconds later, tweets were downloaded, ping sent in notification area
            Expected: No Toast Message or Notification shown.
            Why? tweets should be new, don't need to download and notify the download

            Test 1.2
                - No Toast message shown (expected)
                after 2 mins, exited app, no response (not explicitly not expected)

            Test 2.1
                - Same behavior as 1.1 except it took ~2 seconds to download
                notification sent from Notification Area, not Toast

            Test 2.2
                - Expected behavior, but same behavior as 2.1

        Behavior expected:
     */
}