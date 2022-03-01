package course.labs.lab5_lifecycle_aware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class LifecycleMainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "LifecycleAware"
    }

    private lateinit var model : CounterViewModel
    private lateinit var resetButton: Button
    private lateinit var prButton: Button
    private lateinit var displayText: TextView

    private fun getScreenOrientation(context: Context): String {
        val orientationList = listOf("Portrait","Landscape","Reverse Portrait","Reverse Landscape")
        val orientation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
        return orientationList[orientation]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize reset button, display text, and 'NEXT' button
        resetButton = findViewById<Button>(R.id.ResButton)
        prButton = findViewById<Button>(R.id.PrButton)
        displayText = findViewById<TextView>(R.id.DisplayText)


        // Initialize model for counter using CounterViewModel
        // Below uses a reference to the view model, rather than an instance?
        model = ViewModelProviders.of(this).get(CounterViewModel::class.java)

        // set initial value of counter and orientation
        model.setOrientation(MutableLiveData<String>(getScreenOrientation(this)))
        var counterTextInit = model.iCounter.value.toString()
        displayText.text = model.getOrientation().toString() + '-' + counterTextInit

        // CounterViewModel observes orientation and counter
        val counterObserver = Observer<Int> {
            displayText.text = (displayText.text as String).substringBeforeLast("-") +
                    '-' + it.toString()
        }
        model.iCounter.observe(this, counterObserver)

        val orientationObserver = Observer<String> {
            displayText.text = it + '-' +
                    (displayText.text as String).substringAfterLast("-")
        }
        model.iOrientation.observe(this, orientationObserver)

        model.bindToActivityLifecycle(this)

        // only deals with counter
        prButton.setOnClickListener {
            Log.i(TAG, "Next button clicked")
//            try {
            val updatedCounter = model.getCounter() + 1;
//            } catch (e : Throwable) {
//                Log.i(TAG, e.toString())
//            }
//            Log.i(TAG, updatedCounter.toString())
            // Todo: Increment counter and update display text
            model.setCounter(MutableLiveData(updatedCounter))
            Log.i(TAG, model.getCounter().toString())

            displayText.text = (displayText.text as String).substringBeforeLast("-") +
                    '-' + model.getCounter().toString()
        }

        resetButton.setOnClickListener {
            //Todo: Reset counter and update display text
            model.setCounter(MutableLiveData(0))

            displayText.text = (displayText.text as String).substringBeforeLast("-") +
                    '-' + model.getCounter().toString()
        }
    }
}
