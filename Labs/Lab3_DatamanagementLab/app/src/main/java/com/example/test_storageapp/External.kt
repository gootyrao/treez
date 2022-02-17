package com.example.test_storageapp

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
            return if (Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState) {
                true
            } else false
        }

    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == extStorageState) {
                true
            } else false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external)


        // TODO: Assign the following values from the resources
        // inputText =
        // response =
        // saveButton =
        // readButton =


        saveButton.setOnClickListener {
            //Do the following when save button is clicked


            var fos: FileWriter? = null
            try {
                // TODO: Write the input text to external storage using fos file handler

            } catch (e: IOException) {
                e.printStackTrace()
            }
            finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e("errr","not close")
                    }

                }
            }

            //TODO: Set response and reset input text

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





