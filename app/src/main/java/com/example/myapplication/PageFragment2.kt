package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment


class PageFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_page2, container, false)

        Toast.makeText(requireContext(), "Here, we can read news articles according to their categories", Toast.LENGTH_LONG).show()
        val technology = view.findViewById<CardView>(R.id.c1)
        val entertainment = view.findViewById<CardView>(R.id.c2)
        val sport = view.findViewById<CardView>(R.id.c3)
        val science = view.findViewById<CardView>(R.id.c4)
        val business = view.findViewById<CardView>(R.id.c5)
        val health = view.findViewById<CardView>(R.id.c6)

        technology.setOnClickListener {
            val intent = Intent(this.activity, Technology::class.java)
            startActivity(intent)
        }

        entertainment.setOnClickListener {
            val intent = Intent(this.activity, Entertainment::class.java)
            startActivity(intent)        }
        sport.setOnClickListener {
            val intent = Intent(this.activity, Sports::class.java)
            startActivity(intent)        }
        science.setOnClickListener {
            val intent = Intent(this.activity, Science::class.java)
            startActivity(intent)        }
        business.setOnClickListener {
            val intent = Intent(this.activity, Business::class.java)
            startActivity(intent)        }
        health.setOnClickListener {
            val intent = Intent(this.activity, Health::class.java)
            startActivity(intent)        }
        return view
    }


}