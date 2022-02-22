package com.example.test_storageapp

import android.content.Context
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.*

class External : AppCompatActivity() {
    protected lateinit var inputText: EditText
    protected lateinit var response: TextView
    protected lateinit var saveButton: Button
    protected lateinit var readButton: Button

    private val filename = "SampleFile.txt"
    private val filepath = "MyFileStorage"
    protected lateinit var myExternalFile: File
    internal var myData = ""
    private val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return (Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState);
        }

    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return (Environment.MEDIA_MOUNTED == extStorageState);
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external)


        // Assigns the following values from the resources
         inputText = findViewById<EditText>(R.id.myInputText)
         response = findViewById<TextView>(R.id.response)
         saveButton = findViewById<Button>(R.id.saveExternalStorage)
         readButton = findViewById<Button>(R.id.getExternalStorage)


        saveButton.setOnClickListener {
            //Do the following when save button is clicked


            var fos: FileWriter? = null
            try {
                // TODO: Write the input text to external storage using fos file handler

                myData = inputText.text.toString()

                if (isExternalStorageAvailable && !isExternalStorageReadOnly) {
                    myExternalFile = File(
                        getExternalFilesDir(filepath), filename
                    )
                    val extFileWriter = FileWriter(myExternalFile)
//                    fos = openFileOutput(filepath + "/" + filename,
//                        Context.MODE_PRIVATE)

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e("err","not close")
                    }

                }
            }

            //TODO: Set response and reset input text
            // clear
        }




        readButton.setOnClickListener {

            try {

                // TODO: Read from saved location into myData

                }

            catch (e: IOException) {
                e.printStackTrace()
            }

            // TODO: Set input text and response text

        }


        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            saveButton.isEnabled = false
        } else {
            myExternalFile = File(getExternalFilesDir(filepath), filename)
        }


    }
}





