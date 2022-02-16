package course.labs.intentslab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class ExplicitlyLoadedActivity : Activity() {

    private var mEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.explicitly_loaded_activity)

        // Get a reference to the EditText field
        mEditText = findViewById(R.id.editText)

        // Declare and setup "Enter" button
        val enterButton = findViewById<Button>(R.id.enter_button)
        enterButton.setOnClickListener{ enterClicked() } // Call enterClicked() when pressed

    }

    // Sets result to send back to calling Activity and finishes

    private fun enterClicked() {

        Log.i(TAG, "Entered enterClicked()")

        // TODO - Save user provided input from the EditText field
        var userTextExplAct = mEditText?.text.toString()
        // TODO - Create a new intent and save the input from the EditText field as an extra
        var userTextRetrievedIntent = Intent(this, ActivityLoaderActivity::class.java)
        userTextRetrievedIntent.putExtra("responseTextExplAct", userTextExplAct)
        // TODO - Set Activity's result with result code RESULT_OK
        setResult(RESULT_OK, userTextRetrievedIntent)
        // Finish the Activity
        finish()
    }

    companion object {

        private val TAG = "Lab-Intents"
    }
}
