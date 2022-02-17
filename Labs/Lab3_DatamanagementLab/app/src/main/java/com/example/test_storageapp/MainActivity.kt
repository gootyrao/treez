package com.example.test_storageapp

import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
//import javax.swing.text.View

class MainActivity : AppCompatActivity() {

    protected lateinit var sharedpreferences: SharedPreferences
    protected lateinit var name: TextView
    protected lateinit var uidtv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view : View? = null
        Get(view)

    }

    fun Save(view: View) {
        //TODO: Save name and uidtv to sharedpreference

//        val n = name.text.toString()
//        val e = uidtv.text.toString()
//        val editor = sharedpreferences.edit()
//        editor.putString(Name, n)
//        editor.putString(uid, e)
//        editor.commit()
    }

    fun clear(view: View) {
        //TODO: Reset name and uidtv


//        name = findViewById<View>(R.id.etName) as TextView
//        uidtv = findViewById<View>(R.id.etUid) as TextView
//        name.text = ""
//        uidtv.text = ""

    }

    fun Get(view: View?) {
        //TODO: Get name, shared preferences and uidtv

//        name = findViewById<View>(R.id.etName) as TextView
//        uidtv = findViewById<View>(R.id.etUid) as TextView
//        sharedpreferences = getSharedPreferences(mypreference,
//                Context.MODE_PRIVATE)
//
//        if (sharedpreferences.contains(Name)) {
//            name.text = sharedpreferences.getString(Name, "")
//        }
//        if (sharedpreferences.contains(uid)) {
//            uidtv.text = sharedpreferences.getString(uid, "")
//
//        }


    }

    fun goToAnActivity(view: View) {
        //TODO: Start External activity using intent

//        val intent = Intent(this, External::class.java)
//        startActivity(intent)
    }

    companion object {
        val mypreference = "mypref"
        val Name = "nameKey"
        val uid = "uidkey"
    }
}
