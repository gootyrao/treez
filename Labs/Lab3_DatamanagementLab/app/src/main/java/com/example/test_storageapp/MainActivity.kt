package com.example.test_storageapp

import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import kotlin.io.path.createTempDirectory

//import javax.swing.text.View

class MainActivity : AppCompatActivity() {

    protected lateinit var sharedpreferences: SharedPreferences
    protected lateinit var name: EditText
    protected lateinit var uidtv: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // I don't understand how I pass the view around and how it changes state
        val view : View? = null
        Get(view)

        // TODO: Note to self, buttons are handled by onClick attr in manifest file
    }

    fun Save(view: View) {

        var loginEditor = sharedpreferences.edit()
        // delete last saved values, if contained, then replace with most recent
        loginEditor.remove(Name)
        loginEditor.remove(uid)
        loginEditor.putString(Name, name.text.toString())
        loginEditor.putString(uid, uidtv.text.toString())

        // think connecting to a database and disconnecting
        loginEditor.commit()
    }

    fun clear(view: View) {
        //TODO: Reset name and uidtv

        name.setText("")
        uidtv.setText("")

    }

    fun Get(view: View?) {
        name = findViewById<EditText>(R.id.etName)
        uidtv = findViewById<EditText>(R.id.etUid)

        //TODO: Get name, shared preferences and uidtv
        sharedpreferences = this.getPreferences(MODE_PRIVATE)
//        Log.i(TAG, (sharedpreferences == null).toString())
        // getBoolean, getString(key, def_value), ...
        //      returns def_value on failure
        val tempName = sharedpreferences.getString(Name, "Name")
        val tempUID = sharedpreferences.getString(uid, "UID")
        name.setText(tempName)
        uidtv.setText(tempUID)
    }

    fun goToAnActivity(view: View) {
        //TODO: Start External activity using intent
        val externalIntent = Intent(this, External::class.java)
        startActivity(externalIntent)
    }

    companion object {
        val TAG = "DataManagementActivity"
        val mypreference = "mypref"
        val Name = "nameKey"
        val uid = "uidkey"
    }
}
