package com.example.artapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.artapp.Recyclier.Parent


class CityList : AppCompatActivity() {

    val list = mutableListOf<Parent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)
        initList()

    }

    private fun initList() {
        list.add(Parent("Germany", mutableListOf("Berlin", "Aachen", "Essen", "Mannheim", "Dortmund")))
        list.add(Parent("France", mutableListOf("Montpellier", "Strasbourg", "Bordeaux", "Toulouse")))
        list.add(Parent("Belgium", mutableListOf("Gent", "Oostende", "Antwerpen", "Leuven")))
        list.add(Parent("Norway", mutableListOf("Stavanger", "Oslo", "Bergen")))
        list.add(Parent("Spain", mutableListOf("Valencia", "Vigo")))
        list.add(Parent("Netherlands", mutableListOf("Leeuwarden", "Amsterdam", "Arnhem", "Rotterdam", "Eindhoven")))
        list.add(Parent("Estonia", mutableListOf("Tallinn")))
        list.add(Parent("Italy", mutableListOf("Padova", "Milano")))
    }

}
