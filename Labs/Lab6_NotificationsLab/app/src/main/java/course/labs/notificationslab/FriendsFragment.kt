package course.labs.notificationslab

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class FriendsFragment : ListFragment() {

    private lateinit var mCallback: SelectionListener

    interface SelectionListener {
        fun onItemSelected(position: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the list adapter for this ListFragment
        listAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_activated_1, FRIENDS)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Make sure that the hosting Activity has implemented
        // the SelectionListener callback interface. We need this
        // because when an item in this ListFragment is selected,
        // the hosting Activity's onItemSelected() method will be called.

        try {
            mCallback = context as SelectionListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SelectionListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "Entered onActivityCreated()")
    }

    override fun onListItemClick(l: ListView, view: View, position: Int, id: Long) {

        // Notify the hosting Activity that a selection has been made.
        mCallback.onItemSelected(position)
    }

    companion object {

        private val FRIENDS = arrayOf("ladygaga", "msrebeccablack", "taylorswift13")
        private const val TAG = "Lab-Notifications"
    }

}