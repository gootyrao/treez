/* See https://kotlinlang.org/docs/reference/ for reference material on
 the Kotlin language */

package course.labs.activitylab

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView

class ActivityTwo : Activity() {

    // Lifecycle counters

    // TODO:
    // Create counter variables for onCreate(), onRestart(), onStart() and
    // onResume()
    // You will need to increment these variables' values when their
    // corresponding lifecycle methods get called
    var onCreateCount: Int = 0;
    var onRestartCount: Int = 0;
    var onStartCount: Int = 0;
    var onResumeCount: Int = 0;


    // TODO: Create variables for each of the TextViews
    private lateinit var textViewOnCreate: TextView
    private lateinit var textViewOnRestart: TextView
    private lateinit var textViewOnStart: TextView
    private lateinit var textViewOnResume: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        // TODO: Assign the appropriate TextViews to the TextView variables
        // Hint: Access the TextView by calling Activity's findViewById()
        // textView1 = (TextView) findViewById(R.id.textView1);
        textViewOnCreate = findViewById(R.id.create)
        textViewOnRestart = findViewById(R.id.restart)
        textViewOnStart = findViewById(R.id.start)
        textViewOnResume = findViewById(R.id.resume)


        val closeButton = findViewById<Button>(R.id.bClose)
        closeButton.setOnClickListener {
            // TODO:
            // This function closes Activity Two
            // Hint: use Context's finish() method
            finish() // *** if app doesnt close or gives an error, change syntax

        }

        // Has previous state been saved?
        if (savedInstanceState != null) {

            // TODO:
            // Restore value of counters from saved state
            // Only need 4 lines of code, one for every count variable
            onCreateCount = savedInstanceState.getInt("onCreateCounter");
            onRestartCount = savedInstanceState.getInt("onRestartCounter");
            onStartCount = savedInstanceState.getInt("onStartCounter");
            onResumeCount = savedInstanceState.getInt("onResumeCounter");
        }

        // Emit LogCat message

        Log.i(TAG, "Entered the onCreate() method")

        // TODO:
        // Update the appropriate count variable
        onCreateCount += 1
        // Update the user interface via the displayCounts() method
        displayCounts()
    }

    // Lifecycle callback methods overrides

    public override fun onStart() {
        super.onStart()

        // Emit LogCat message
        Log.i(TAG, "Entered the onStart() method")

        // TODO:
        // Update the appropriate count variable
        onStartCount += 1
        // Update the user interface
        displayCounts()
    }

    public override fun onResume() {
        super.onResume()

        // Emit LogCat message
        Log.i(TAG, "Entered the onResume() method")
        // Follow the previous 2 examples provided


        // TODO:
        // Update the appropriate count variable
        onResumeCount += 1
        // Update the user interface
        displayCounts()
    }

    public override fun onPause() {
        super.onPause()

        // Emit LogCat message
        Log.i(TAG, "Entered the onPause() method")
        // Follow the previous 2 examples provided

    }

    public override fun onStop() {
        super.onStop()

        // Emit LogCat message
        Log.i(TAG, "Entered the onStop() method")
        // Follow the previous 2 examples provided

        // Save bundle here? Consult slides
    }

    public override fun onRestart() {
        super.onRestart()

        // Emit LogCat message
        Log.i(TAG, "Entered the onRestart() method")
        // Follow the previous 2 examples provided


        // TODO:
        // Update the appropriate count variable
        onRestartCount += 1
        // Update the user interface
        displayCounts()
    }

    public override fun onDestroy() {
        super.onDestroy()

        // Emit LogCat message
        Log.i(TAG, "Entered the onDestroy() method")
        // Follow the previous 2 examples provided

    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {

        // TODO:
        // Save counter state information with a collection of key-value pairs
        // 4 lines of code, one for every count variable

        savedInstanceState.putInt("onCreateCounter", onCreateCount)
        savedInstanceState.putInt("onResumeCounter", onResumeCount)
        savedInstanceState.putInt("onRestartCounter", onRestartCount)
        savedInstanceState.putInt("onStartCounter", onStartCount)

        // call superclass to save any view hierarchy
        // might need to get rid of this code
        super.onSaveInstanceState(savedInstanceState)
    }

    // Updates the displayed counters
    private fun displayCounts() {

        textViewOnCreate.setText("onCreate() called " + onCreateCount.toString() + " times")
        textViewOnRestart.setText("onRestart() called " + onRestartCount.toString() + " times")
        textViewOnResume.setText("onResume() called " + onResumeCount.toString() + " times")
        textViewOnStart.setText("onStart() called " + onStartCount.toString() + " times")
    }

    companion object {

        private val RESTART_KEY = "restart"
        private val RESUME_KEY = "resume"
        private val START_KEY = "start"
        private val CREATE_KEY = "create"

        // String for LogCat documentation
        private val TAG = "Lab-ActivityTwo"
    }
}
