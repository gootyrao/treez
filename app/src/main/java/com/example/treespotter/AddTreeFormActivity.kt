package com.example.treespotter

import android.app.Activity
import android.os.Bundle
import android.widget.EditText

class AddTreeFormActivity : Activity() {

    private var treeSpecies : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.treeform)

        treeSpecies = findViewById<EditText>(R.id.editSpecies)
    }
}