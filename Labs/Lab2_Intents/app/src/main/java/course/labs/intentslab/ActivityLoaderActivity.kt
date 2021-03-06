package course.labs.intentslab

import android.app.Activity
import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ActivityLoaderActivity : Activity() {

    // TextView that displays user-entered text from ExplicitlyLoadedActivity runs
    private var mUserTextView: TextView? = null

    // TODO - return a base intent for viewing a URL
    // (HINT:  second parameter uses Uri.parse())
    // You will need to write the get() = null
    // into the code block get() {}
    val baseIntent: Intent?
        get() {
            var implicitIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:5555555555"))
            return implicitIntent
        }

//    private fun getBaseIntent() {
//        return Intent(Intent.ACTION_DIAL, Uri.parse("tel:5555555555"))
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader_activity)

        // Get reference to the textView
        mUserTextView = findViewById(R.id.textView1)

        // Declare and setup Explicit Activation button
        val explicitActivationButton = findViewById<Button>(R.id.explicit_activation_button)
        explicitActivationButton.setOnClickListener{ startExplicitActivation() } // Call startExplicitActivation() when pressed


        // Declare and setup Implicit Activation button
        val implicitActivationButton = findViewById<Button>(R.id.implicit_activation_button)
        implicitActivationButton.setOnClickListener{ startImplicitActivation() } // Call startImplicitActivation() when pressed

    }


    // Start the ExplicitlyLoadedActivity

    private fun startExplicitActivation() {

        Log.i(TAG, "Entered startExplicitActivation()")

        // TODO - Create a new intent to launch the ExplicitlyLoadedActivity class
        var explicitIntent = Intent(this, ExplicitlyLoadedActivity::class.java)
        // TODO - Start an Activity using that intent and the request code defined above
        startActivityForResult(explicitIntent, GET_TEXT_REQUEST_CODE)
    }

    // Start a Browser Activity to view a web page or its URL

    private fun startImplicitActivation() {

        Log.i(TAG, "Entered startImplicitActivation()")

        // TODO - Implement getBaseIntent() to Create a base intent for viewing a URL
        val baseImplicitIntent = baseIntent

        // TODO - Create a chooser intent, for choosing which Activity
        // will carry out the baseIntent
        // (HINT: Use the Intent class' createChooser() method)
        val dialChooserIntent = Intent.createChooser(baseImplicitIntent, "Call With")
        //TODO - Log the chooser intent action
        Log.i(TAG, dialChooserIntent.action.toString())
        // TODO - Start the chooser Activity, using the chooser intent
        startActivity(dialChooserIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        Log.i(TAG, "Entered onActivityResult()")

        // TODO - Process the result only if this method received both a
        // RESULT_OK result code and a recognized request code
        // If so, update the Textview showing the user-entered text.
        if (resultCode == RESULT_OK && requestCode == GET_TEXT_REQUEST_CODE) {

            var textFromExplAct = data.getStringExtra("responseTextExplAct")
            mUserTextView?.setText(textFromExplAct)
        }

    }

    companion object {

        private const val GET_TEXT_REQUEST_CODE = 1
        //        private val URL = "http://www.google.com"
        private const val URL = "tel:5555555555"
        private const val TAG = "Lab-Intents"

        // For use with app chooser
        private val CHOOSER_TEXT = "Load $URL with:"
    }
}
