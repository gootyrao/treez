package course.labs.lab5_lifecycle_aware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.TextView
import android.content.Context
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class LifecycleMainActivity : AppCompatActivity() {

    private lateinit var model : CounterViewModel

    private fun getScreenOrientation(context: Context): String {
        val orientationList = listOf("Portrait","Landscape","Reverse Portrait","Reverse Landscape")
        val orientation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
        return orientationList[orientation]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ToDo: Implement your own logic to display appropriate text ,increase and reset the counter

        //Todo: Initialize reset button, display text, and 'NEXT' button
        val resetButton: Button = findViewById<Button>(R.id.ResButton) as Button
        val prButton:Button = findViewById<Button>(R.id.PrButton) as Button
        val displayText: TextView = findViewById<TextView>(R.id.DisplayText) as TextView

        // Todo: Initialize model for counter using CounterViewModel
         model = ViewModelProvider(this).get(CounterViewModel::class.java)

        prButton.setOnClickListener {
            //Todo: Increment counter and update display text

        }

        resetButton.setOnClickListener {
            //Todo: Reset counter and update display text
        }
    }


}
