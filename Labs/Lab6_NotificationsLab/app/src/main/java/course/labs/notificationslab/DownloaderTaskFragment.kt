package course.labs.notificationslab

import android.app.*
import android.app.Activity.RESULT_CANCELED
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.icu.number.IntegerWidth
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import java.io.*
import kotlin.reflect.typeOf

class DownloaderTaskFragment : Fragment() {
    private var mDownloaderTask: DownloaderTask? = null
    private var mCallback: DownloadFinishedListener? = null
//    private val notificationManager: NotificationManager =
//        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var mContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        // TODO: (Done, needs test) Create new DownloaderTask that "downloads" data
        mDownloaderTask = DownloaderTask()

        // TODO: (Done, needs test) Retrieve arguments from DownloaderTaskFragment
        // Prepare them for use with DownloaderTask.
        var argsDownloaderTaskFragment = this.requireArguments().get("friends") // Bundle
//        Log.i("NotificationsLab", argsDownloaderTaskFragment.toString())
        var feedsIntArr = arrayOf(argsDownloaderTaskFragment)
        var feeds = feedsIntArr as Array<Int?>

        mDownloaderTask!!.execute(*feeds)

        return inflater.inflate(R.layout.feed, container, false)
    }

    // Assign current hosting Activity to mCallback
    // Store application context for use by downloadTweets()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        // Make sure that the hosting activity has implemented
        // the correct callback interface.

        try {

            mCallback = context as DownloadFinishedListener

        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DownloadFinishedListener")
        }
    }

    // Null out mCallback
    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }


    // you will not have to implement doInBackground and onPostExecute
    inner class DownloaderTask : CoroutineAsyncTask<Int?, Void?, Array<String?>>() {
        override fun doInBackground(vararg params: Int?): Array<String?> {
            return downloadTweets(params as Array<Int>)
        }

        // Simulates downloading Twitter data from the network
        private fun downloadTweets(resourceIDS: Array<Int>): Array<String?> {
            val simulatedDelay = 2000
            val feeds = arrayOfNulls<String>(resourceIDS.size)
            var downLoadCompleted = false
            try {
                for (idx in resourceIDS.indices) {
                    var `in`: BufferedReader
                    try {
                        // Pretend downloading takes a long time
                        Thread.sleep(simulatedDelay.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    val inputStream: InputStream = mContext.resources.openRawResource(
                            resourceIDS[idx])
                    `in` = BufferedReader(InputStreamReader(inputStream))
                    var readLine: String?
                    val buf = StringBuffer()
                    while (`in`.readLine().also { readLine = it } != null) {
                        buf.append(readLine)
                    }
                    feeds[idx] = buf.toString()
                    `in`.close()
                }
                downLoadCompleted = true
                saveTweetsToFile(feeds)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Notify user that downloading has finished
            notify(downLoadCompleted)
            return feeds
        }

        // If necessary, notifies the user that the tweet downloads are
        // complete. Sends an ordered broadcast back to the BroadcastReceiver in
        // MainActivity to determine whether the notification is necessary.
        private fun notify(success: Boolean) {
            val restartMainActivityIntent = Intent(mContext,
                    MainActivity::class.java)
            restartMainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Sends an ordered broadcast to determine whether MainActivity is
            // active and in the foreground. Creates a new BroadcastReceiver
            // to receive a result indicating the state of MainActivity

            // TODO: The Action for this broadcast Intent is MainActivity.DATA_REFRESHED_ACTION
            // The result, MainActivity.IS_ALIVE, indicates that MainActivity is
            // active and in the foreground.
            mContext.sendOrderedBroadcast(Intent(
                    MainActivity.DATA_REFRESHED_ACTION), null,
                    object : BroadcastReceiver() {
                        val failMsg = mContext
                                .getString(R.string.download_failed_string)
                        val successMsg = mContext
                                .getString(R.string.download_succes_string)
                        val notificationSentMsg = mContext
                                .getString(R.string.notification_sent_string)

                        override fun onReceive(context: Context, intent: Intent) {

                            // TODO: (Done, needs testing) Check whether or not the MainActivity
                            // received the broadcast
                            if (resultCode == RESULT_CANCELED) {

                                createNotificationChannel()

                                // TODO: (Done, needs testing) If not, create a PendingIntent using the
                                // restartMainActivityIntent and set its flags
                                // to FLAG_UPDATE_CURRENT
                                val mContentIntent = PendingIntent.getActivity(context,
                                    0,
                                    restartMainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT
                                )

                                // Uses R.layout.custom_notification for the
                                // layout of the notification View. The xml
                                // file is in res/layout/custom_notification.xml
                                val mContentView = RemoteViews(
                                        mContext.packageName,
                                        R.layout.custom_notification)

                                // TODO: (Done, needs testing) Set the notification View's text to
                                // reflect whether the download completed
                                // successfully
                                val notifViewText = if (success) successMsg else failMsg

                                // TODO: Use the Notification.Builder class to
                                // create the Notification. You will have to set
                                // several pieces of information. You can use
                                // android.R.drawable.stat_sys_warning
                                // for the small icon. You should also
                                // setAutoCancel(true).
                                val notificationBuilder = Notification.Builder(
                                    context, channelID
                                )
                                    .setCustomContentView(mContentView)
                                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                                    .setAutoCancel(true)
//                                    .setContentTitle() // Don't think I need this, keeping it here
                                    .setContentText(notifViewText)
                                    .setContentIntent(mContentIntent)

                                // TODO: Send the notification
                                // get reference to the notification manager, call notify on it
                                val notificationManager = mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                                notificationManager.notify(
                                    MY_NOTIFICATION_ID,
                                    notificationBuilder.build()
                                )
                            } else {
                                Toast.makeText(mContext,
                                        if (success) successMsg else failMsg,
                                        Toast.LENGTH_LONG).show()
                            }
                        }
                    }, null, 0, null, null)
        }

        private val channelID = "my_channel_01"
        private fun createNotificationChannel() {
            // TODO: Create Notification Channel with id channelID,
            // name R.string.channel_name
            // and description R.string.channel_description of high importance
            val channelName = getString(R.string.channel_name)
            var notifChannel = NotificationChannel(channelID,
                channelName, NotificationManager.IMPORTANCE_HIGH).apply {
                    description = getString(R.string.channel_description)
            }

            val notificationManager = mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notifChannel)
        }

        // Saves the tweets to a file
        private fun saveTweetsToFile(result: Array<String?>) {
            var writer: PrintWriter? = null
            try {
                val fos = mContext.openFileOutput(
                        MainActivity.TWEET_FILENAME, Context.MODE_PRIVATE)
                writer = PrintWriter(BufferedWriter(
                        OutputStreamWriter(fos)))
                for (s in result) {
                    writer.println(s)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                writer?.close()
            }
        }

        // Pass newly available data back to hosting Activity
        // using the DownloadFinishedListener interface
        override fun onPostExecute(result: Array<String?>?) {
            super.onPostExecute(result)
            if (null != mCallback) {
                mCallback!!.notifyDataRefreshed(result)
            }
        }
    }

    companion object {
        private const val MY_NOTIFICATION_ID = 11151990
    }
}