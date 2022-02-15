/* See https://kotlinlang.org/docs/reference/ for reference material on
 the Kotlin language */

package course.labs.activitylab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView

class ActivityOne : Activity() {


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
    var textViewOnCreate = null // not sure what the default value is supposed to be here
    var textViewOnRestart = null
    var textViewOnStart = null
    var textViewOnResume = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        // TODO: Assign the appropriate TextViews to the TextView variables
        // Hint: Access the TextView by calling Activity's findViewById()
        textViewOnCreate = findViewById(R.id.create)
        textViewOnRestart = findViewById(R.id.restart)
        textViewOnStart = findViewById(R.id.start)
        textViewOnResume = findViewById(R.id.resume)


        val launchActivityTwoButton = findViewById<Button>(R.id.bLaunchActivityTwo)
        launchActivityTwoButton.setOnClickListener {
            // TODO:
            // Launch Activity Two
            // Hint: use Context's startActivity() method

            // Create an intent stating which Activity you would like to start
            val actTwoIntent = Intent(
                Intent.ACTION_MAIN,
                ActivityTwo:class.java) //          *** NOT SURE if this is right ***
            // Launch the Activity using the intent
            startActivity(actTwoIntent)
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
        // Update the user interface via the displayCounts() method
        onCreateCount += 1
        displayCounts()
    }

    // Lifecycle callback overrides

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
        // Save state information with a collection of key-value pairs
        bundle.putInt("onCreateCounter", onCreateCount)
        bundle.putInt("onResumeCounter", onResumeCount)
        bundle.putInt("onRestartCounter", onRestartCount)
        bundle.putInt("onStartCounter", onStartCount)

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(bundle)
    }

    // Updates the displayed counters
    private fun displayCounts() {
        // TODO:
        // set text for text view variables
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
        private val TAG = "Lab-ActivityOne"
    }
}
