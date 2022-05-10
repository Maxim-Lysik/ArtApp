package com.example.artapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.euryperez.nested_recyclerview.MainFragment

class CitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        supportFragmentManager.beginTransaction().replace(R.id.place_holder, MainFragment())
            .commit()

    }


    override fun onBackPressed() {
        finishAffinity()
        finish()
    }


    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tag).addToBackStack("").commit()
    }
}