package com.example.artapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Boolean


class on_boarding : AppCompatActivity() {

    var prevStarted = "yes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(R.id.on_boarding_place_holder, explore_cities())
            .commit()


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    override fun onResume() {
        super.onResume()
        val sharedpreferences = getSharedPreferences("com.example.artapp", Context.MODE_PRIVATE)
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean(prevStarted, Boolean.TRUE)
            editor.apply()
        } else {
            moveToSecondary()
        }
    }


    fun moveToSecondary() {
        val intent = Intent(this, CitiesActivity::class.java)
        startActivity(intent)
    }


}