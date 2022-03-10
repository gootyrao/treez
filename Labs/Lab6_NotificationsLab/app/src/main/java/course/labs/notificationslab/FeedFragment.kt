package course.labs.notificationslab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FeedFragment : Fragment() {

    private var mTextView: TextView? = null
    private var feedFragmentData: FeedFragmentData? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Read in all Twitter feeds
        if (null == feedFragmentData) {
            feedFragmentData = FeedFragmentData(requireActivity())
        }
    }

    // Display Twitter feed for selected feed
    fun updateFeedDisplay(position: Int) {

        Log.i(TAG, "Entered updateFeedDisplay()")

        mTextView = requireView().findViewById<View>(R.id.feed_view) as TextView
        mTextView?.text = feedFragmentData!!.getFeed(position)

    }

    companion object {
        private const val TAG = "Lab-Notifications"
    }

}