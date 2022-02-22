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
import java.nio.charset.Charset

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
         saveButton = findViewById<Button>(R.id.saveExternalStorage) as Button
         readButton = findViewById<Button>(R.id.getExternalStorage) as Button


        saveButton.setOnClickListener {
            //Do the following when save button is clicked
            Log.i(TAG, "save button clicked")


            var fos: FileWriter? = null
            try {
                // TODO: Write the input text to external storage using fos file handler

                myData = inputText.text.toString()

                if (isExternalStorageAvailable && !isExternalStorageReadOnly) {
                    myExternalFile = File(
                        getExternalFilesDir(filepath), filename
                    )
                    if (!myExternalFile.exists())
                        myExternalFile.createNewFile()

                    val extFileWriter = FileWriter(myExternalFile)
                    extFileWriter.write(myData)
                    extFileWriter.close()

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

            //TODO: Set response and reset input text (clear)

            // 'filename' data has been saved to external storage
            var responseMsg = filename + "has been saved to external storage..."
            response.text = responseMsg

            inputText.setText("")
        }




        readButton.setOnClickListener {

            try {

                // TODO: Read from saved location into myData
                    if (isExternalStorageAvailable) {
                        myExternalFile = File(
                            getExternalFilesDir(filepath), filename
                        )
                        if (myExternalFile.exists()) {
                            // read file if exists
//                            val extFileInputStream = FileReader(myExternalFile)
//                            extFileInputStream. write(myData)
//                            extFileInputStream.close()
                            // Note to self, not recommended for files over 2 GB
                            myData = myExternalFile.readText(Charset.defaultCharset())
                        }
                    }
                }

            catch (e: IOException) {
                e.printStackTrace()
            }

            // TODO: Set input text and response text
            // 'filename' data has been retrieved from external storage
            var responseMsg = filename + "has been retrieved from external storage..."
            response.text = responseMsg

            inputText.setText(myData)
        }


        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            saveButton.isEnabled = false
        } else {
            myExternalFile = File(getExternalFilesDir(filepath), filename)
        }


    }

    companion object {
        private val TAG = "ExternalActivity"
    }
}





