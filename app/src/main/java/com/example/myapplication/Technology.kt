package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Technology : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_technology)
        val toolbar: Toolbar = findViewById<View>(R.id.tool) as Toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        Toast.makeText(this, "Technology category clicked", Toast.LENGTH_SHORT).show()
    }
}