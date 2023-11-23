package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        tabLayout = findViewById<TabLayout>(R.id.sliding_tabs)
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val titlesarray = arrayOf("My feed","Discover")
            tab.text = titlesarray[position]
        }.attach()

        retrieveToken()
    }
    private fun retrieveToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("FCM registration ", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
           val token = task.result
            Log.d("FCM registration ", "Fetching FCM registration token success- $token")

        })
    }
    override fun onBackPressed() {
        if (tabLayout.selectedTabPosition != 0) {
            tabLayout.getTabAt(0)?.select()
        } else {
            super.onBackPressed()
        }
    }

}