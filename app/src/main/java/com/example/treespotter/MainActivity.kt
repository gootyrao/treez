package com.example.treespotter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.treespotter.ui.theme.TreeSpotterTheme

class MainActivity : ComponentActivity() {

    /*
    LinearLayout

    TextView for title
    Button addTreeButton
    Button localTreesButton


     */

    private var titleTextView : TextView? = null
    private var homeImg : ImageView? = null
    private var addTreeButton : Button? = null
    private var localTreesButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Setting up UI
        setContentView(R.layout.main)

        titleTextView = findViewById<TextView>(R.id.titleText)

        homeImg = findViewById<ImageView>(R.id.imageView2)

        addTreeButton = findViewById<Button>(R.id.addTreeButton)

        localTreesButton = findViewById<Button>(R.id.localTreesButton)

        addTreeButton?.setOnClickListener {
            var addTreeIntent = Intent(applicationContext, AddTreeFormActivity::class.java)
            startActivity(addTreeIntent)
        }

        localTreesButton?.setOnClickListener {
            // TODO: Add name of maps activity in second parameter
            var localTreesIntent = Intent(applicationContext, GoogleMapsActivity::class.java)
            startActivity(localTreesIntent)
        }

    }
}

/*
Came with the template, can be deleted


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TreeSpotterTheme {
        Greeting("Android")
    }
}

 */