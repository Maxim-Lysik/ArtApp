package com.example.artapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.adapters.SavedAdaptor
import com.example.artapp.data.response.SavedItem
import com.example.artapp.toroom.AppDatabase
import com.example.artapp.toroom.SavedItemsDao
import kotlinx.android.synthetic.main.activity_for_saved.*

class ActivityForSaved : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var saveddao: SavedItemsDao? = null
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_saved)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSaved)

        val linearLayoutManager = LinearLayoutManager(
            this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        mapsViewModel.our_saveditems.observe(this, Observer { items ->
            recyclerView.adapter = SavedAdaptor(items as ArrayList<SavedItem>, this)
            if (items.size > 0) {
                no_items.visibility = View.INVISIBLE
                no_found_text.visibility = View.INVISIBLE
            }
        })


        floating_button.setOnClickListener {
            val intent =
                Intent(this@ActivityForSaved, CitiesActivity::class.java)
            startActivity(intent)
        }


    }
}